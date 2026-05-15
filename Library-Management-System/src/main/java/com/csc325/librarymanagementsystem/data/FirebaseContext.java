package com.csc325.librarymanagementsystem.data;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Loan;

public class FirebaseContext {
    public static final String BOOKS_COLLECTION = "books";
    private final Firestore db; //static so that FirebaseContext always uses the same db rather than per-class instantiation

    private final LoanContext loans;
    private final CheckoutContext checkouts;
    private final UserContext users;
    private final NotificationContext notifications;

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = db.collection(BOOKS_COLLECTION).get();
            QuerySnapshot querySnapshot = query.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                // convert firestore to book
                Book book = document.toObject(Book.class);
                bookList.add(book);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return bookList;
    }

    public Book getBookById(String bookId) {
        if (bookId == null || bookId.isBlank()) {return null;}

        try {
            ApiFuture<DocumentSnapshot> query = db.collection(BOOKS_COLLECTION).document(bookId).get();
            DocumentSnapshot document = query.get();
            if (!document.exists()) {
                return null;
            }
    
            Book book = document.toObject(Book.class);
            assert book != null;
            book.setBookId(document.getId()); // firestore document id is the book id but doesnt actually set the bookId field, so must set manually
            return book;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving book " + bookId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addBook(List<Book> newBooks) {
        if (newBooks == null || newBooks.isEmpty()) {
            return false;
        }

        boolean addedBook = false;


        for (Book book : newBooks) {
            if (book == null) {
                throw new IllegalArgumentException("book cannot be null.");
            }

            DocumentReference bookDocument;
            if (book.getBookId() == null || book.getBookId().isBlank()) {
                bookDocument = db.collection(BOOKS_COLLECTION).document();
                book.setBookId(bookDocument.getId());
            } else {
                bookDocument = db.collection(BOOKS_COLLECTION).document(book.getBookId());
            }

            try {
                bookDocument.create(book).get();
                addedBook = true;
            } catch (ExecutionException | InterruptedException e) {
                System.err.println("Error adding book " + book.getBookId() + ": " + e.getMessage());
            }
        }
        return addedBook;
    }

    //@Overload
    //overloaded method to add a single book rather than a list of books
    public boolean addBook(Book book) {
        return addBook(List.of(book));
    }

    public boolean adjustStock(String bookId, int amount) {
        // amount is the increment or decrement you are changing the quantity by
        if (bookId == null || bookId.isBlank()) {return false;}

        try {
            db.collection(BOOKS_COLLECTION).document(bookId)
              .update("quantity", FieldValue.increment(amount))
              .get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error adjusting stock for " + bookId + ": " + e.getMessage());
            return false;
        }
    }

    public boolean processReturn(String loanId) {
        if (loanId == null || loanId.isBlank()) {return false;}

        Loan loan = loans.getLoanByLoanId(loanId);
        if (loan == null) {
            System.err.println("Error returning loan: " + loanId + " - Loan not found");
            return false;
        }
        if (loan.isReturned()) {
            System.err.println("Error returning loan: " + loanId + " - Cannot return already returned loan");
            return false;
        }

        Book book = getBookById(loan.getBookId());
        if (book == null) {
            System.err.println("Error returning loan: " + loanId + " - Book " + loan.getBookId() + " not found");
            return false;
        }

        if (!loans.markLoanReturned(loanId)) {return false;} // return false if loan fails to get marked as returned
        return adjustStock(loan.getBookId(), 1); // adjust stock of book +1
    }

    public Book getBookAt(int n) {
        // this method sorts the collection by the document id, which means the sorting will be unexpected and not actually reflect the order you see them in firestore. This works for now
        // since this is only used for one purpose, but this needs to be changed later if it becomes something that needs to be used more accurately to the firestore itself.
        if (n < 0) {return null;}

        try {
            ApiFuture<QuerySnapshot> query = db.collection(BOOKS_COLLECTION)
                    .orderBy(FieldPath.documentId()) // sort the collection by documentId since firebase doesnt actually use index positions.
                    .offset(n)
                    .limit(1)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (documents.isEmpty()) {
                return null; // n was out of range
            }

            QueryDocumentSnapshot document = documents.get(0);
            Book book = document.toObject(Book.class);
            book.setBookId(document.getId()); // firestore document id is the book id but doesnt actually set the bookId field, so must set manually
            return book;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving book at index " + n + ": " + e.getMessage());
        }
        return null;
    }

    public int getCollectionSize(String collectionId) {
        if (collectionId == null || collectionId.isBlank()) {return 0;}

        try {
            CollectionReference collection = db.collection(collectionId);
            AggregateQuerySnapshot countSnapshot = collection.count().get().get();
            return (int) countSnapshot.getCount(); //getCount returns a long, casting as int
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error getting collection size " + collectionId + ": " + e.getMessage());
        }
        return 0;
    }

    public LoanContext loans() { return loans;}

    public CheckoutContext checkouts() { return checkouts; }

    public UserContext users() { return users; }

    public NotificationContext notifications() { return notifications; }

    public FirebaseContext() {
        this.db = FirebaseInitializer.getFirestore();
        this.loans = new LoanContext(db);
        this.checkouts = new CheckoutContext(db);
        this.users = new UserContext(db);
        this.notifications = new NotificationContext(db);
    }



}
