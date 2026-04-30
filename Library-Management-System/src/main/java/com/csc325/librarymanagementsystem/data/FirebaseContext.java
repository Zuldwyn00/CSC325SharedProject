package com.csc325.librarymanagementsystem.data;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Loan;

public class FirebaseContext {
    private final String BOOKS_COLLECTION = "books";
    private static Firestore db; //static so that FirebaseContext always uses the same db rather than per-class instantiation

    // Fake data, remove later
    private final List<Book> books = new ArrayList<>(FakeData.getBooks());
    private final List<User> users = new ArrayList<>(FakeData.getUsers());
    private final List<Loan> loans = new ArrayList<>(FakeData.getLoans());

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            // Get a reference to the collection and fetch all documents
            ApiFuture<QuerySnapshot> query = db.collection(BOOKS_COLLECTION).get();
            QuerySnapshot querySnapshot = query.get(); // Blocking call to get data

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                // Converts the Firestore document directly into your Book object
                // Note: Ensure your Book class has a no-argument constructor
                Book book = document.toObject(Book.class);
                bookList.add(book);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return bookList;
    }

    public Book getBookById(String bookId) {
        if (bookId == null) {return null;}

        for (Book book: books) {
            if (bookId.equals(book.getBookId())) {return book;}
        }
        return null;
    }

    public boolean adjustStock(String bookId, int amount) {
        return false;
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

    public FirebaseContext() {
        initializeFirebase();
    }

    private void initializeFirebase() {
        // Only initialize if there isn't already an active app instance
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                FileInputStream serviceAccount =
                        new FileInputStream("src/main/resources/com.csc325.librarymanagementsystem/firebase/key.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                db = FirestoreClient.getFirestore();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        } else if (db == null) {
            db = FirestoreClient.getFirestore();
        }
    }



}
