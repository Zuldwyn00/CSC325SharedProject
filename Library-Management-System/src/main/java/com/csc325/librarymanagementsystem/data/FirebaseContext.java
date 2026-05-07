package com.csc325.librarymanagementsystem.data;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Loan;

public class FirebaseContext {
    private final String BOOKS_COLLECTION = "books";
    private final Firestore db; //static so that FirebaseContext always uses the same db rather than per-class instantiation

    private final LoanContext loans;
    private final CheckoutContext checkouts;

    // Fake data, remove later
    private final List<User> users = new ArrayList<>(FakeData.getUsers());

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
            DocumentSnapshot document = db.collection(BOOKS_COLLECTION).document(bookId).get().get();
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
            if (book == null || book.getBookId() == null || book.getBookId().isBlank()) {
                throw new IllegalArgumentException("bookId cannot be null or blank.");
            }

            DocumentReference bookDocument = db.collection(BOOKS_COLLECTION).document(book.getBookId());
            try {
                bookDocument.create(book).get(); //firebase already detects duplicate Document ID's, no need to have a manual check
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

    /**
     * Processes a return for the given loan. Marks the loan as returned and
     * increments the book's stock by 1. Returns false on any validation failure
     * or Firestore error.
     *
     * Lives on FirebaseContext because this is the data-layer facade that
     * already aggregates loans, checkouts, and book operations. Keeping the
     * orchestration here means CheckoutContext and LoanContext stay focused
     * on their own collections.
     *
     * Note: writes are sequential, not transactional. If the loan update
     * succeeds but the stock increment fails, Firebase will be left inconsistent.
     */
    public boolean processReturn(String loanId) {
        if (loanId == null || loanId.isBlank()) {return false;}

        Loan loan = loans.getLoanById(loanId);
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

    public User findUserByIdentifier(String identifier) {
        if (identifier == null) {return null;}

        for (User user: users) {
            if (identifier.equals(user.getLibraryId())
                || identifier.equals(user.getEmail()))
            {return user;}
        }
        return null;
    }

    public User findUserByUserID(String id) {
        if (id == null) {return null;}

        for (User user: users) {
            if (id.equals(user.getUserId())) {return user;}
        }
        return null;
    }

    public LoanContext loans() { return loans;}

    public CheckoutContext checkouts() { return checkouts; }

    public FirebaseContext() {
        this.db = FirebaseInitializer.getFirestore();
        this.loans = new LoanContext(db);
        this.checkouts = new CheckoutContext(db);
    }



}
