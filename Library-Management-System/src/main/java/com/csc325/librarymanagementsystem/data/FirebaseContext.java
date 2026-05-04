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

import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.model.Book;

public class FirebaseContext {
    private final String BOOKS_COLLECTION = "books";
    private final String USERS_COLLECTION = "users";
    private final Firestore db; //static so that FirebaseContext always uses the same db rather than per-class instantiation

    private final LoanContext loans;
    private final CheckoutContext checkouts;

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
            book.setBookId(document.getId()); // firestore document id is the book id but doesn't actually set the bookId field, so must set manually
            return book;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving book " + bookId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addNewBook(List<Book> newBooks) {
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
                book.setBookId(bookDocument.getId()); // if the bookId is not directly given, generate it with firestore and set it for the object in memory
            } else {
                bookDocument = db.collection(BOOKS_COLLECTION).document(book.getBookId());
            }

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
    public boolean addNewBook(Book book) {
        return addNewBook(List.of(book));
    }

    public boolean adjustStock(String bookId, int amount) {
        // amount is the adjustment to the stock, not what you are setting the stock to
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

    public User findUserByIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {return null;}

        // Reason: Firestore can't OR across two different fields, so try libraryId first,
        // then fall back to email. Two roundtrips, no composite index required.
        User byLibraryId = queryFirstUserBy("libraryId", identifier);
        if (byLibraryId != null) {return byLibraryId;}

        return queryFirstUserBy("email", identifier);
    }

    public User findUserByUserID(String id) {
        if (id == null || id.isBlank()) {return null;}

        try {
            DocumentSnapshot document = db.collection(USERS_COLLECTION).document(id).get().get();
            if (!document.exists()) {return null;}

            User user = document.toObject(User.class);
            assert user != null;
            user.setUserId(document.getId());
            return user;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving user " + id + ": " + e.getMessage());
        }
        return null;
    }

    private User queryFirstUserBy(String field, String value) {
        try {
            ApiFuture<QuerySnapshot> query = db.collection(USERS_COLLECTION)
                    .whereEqualTo(field, value)
                    .limit(1)
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            if (documents.isEmpty()) {return null;}

            QueryDocumentSnapshot document = documents.get(0);
            User user = document.toObject(User.class);
            user.setUserId(document.getId());
            return user;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error querying users by " + field + "=" + value + ": " + e.getMessage());
            return null;
        }
    }

    public boolean addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null.");
        }
        if (user.getLibraryPin() == null || user.getLibraryPin().isBlank()) {
            throw new IllegalArgumentException("user libraryPin cannot be null or blank.");
        }

        // Reason: hash plaintext PIN before persisting; libraryPin is @Exclude'd from
        // serialization so only pinHash + pinSalt land in the Firestore document.
        String salt = PinHasher.generateSalt();
        user.setPinSalt(salt);
        user.setPinHash(PinHasher.hash(user.getLibraryPin(), salt));

        DocumentReference userDocument;
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            userDocument = db.collection(USERS_COLLECTION).document();
            user.setUserId(userDocument.getId());
        } else {
            userDocument = db.collection(USERS_COLLECTION).document(user.getUserId());
        }

        try {
            userDocument.create(user).get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error adding user " + user.getUserId() + ": " + e.getMessage());
            return false;
        }
    }

    public LoanContext loans() { return loans;}

    public CheckoutContext checkouts() { return checkouts; }

    public FirebaseContext() {
        this.db = FirebaseInitializer.getFirestore();
        this.loans = new LoanContext(db);
        this.checkouts = new CheckoutContext(db);
    }



}
