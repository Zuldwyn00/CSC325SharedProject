package com.csc325.librarymanagementsystem;

import com.csc325.librarymanagementsystem.data.CheckoutContext;
import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.data.FirebaseInitializer;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Loan;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class resetDatabaseFromFakeData {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FirebaseContext firebaseContext = new FirebaseContext();
        Firestore firestore = FirebaseInitializer.getFirestore();

        resetBooksCollection(firestore, firebaseContext);
        resetLoansCollection(firestore, firebaseContext);
        resetCheckoutCollection(firestore, firebaseContext);

        System.out.println("Firebase reset from fake data is complete.");
    }

    private static void resetBooksCollection(
            Firestore firestore,
            FirebaseContext firebaseContext
    ) throws ExecutionException, InterruptedException {
        clearCollectionExceptFirstDocument(firestore, "books");
        firebaseContext.addBook(FakeData.getBooks());
    }

    private static void resetLoansCollection(
            Firestore firestore,
            FirebaseContext firebaseContext
    ) throws ExecutionException, InterruptedException {
        clearCollectionExceptFirstDocument(firestore, "loans");
        for (Loan loan : FakeData.getLoans()) {
            firebaseContext.loans().recordLoan(loan);
        }
    }

    private static void resetCheckoutCollection(
            Firestore firestore,
            FirebaseContext firebaseContext
    ) throws ExecutionException, InterruptedException {
        clearCollectionExceptFirstDocument(firestore, CheckoutContext.CHECKOUTS_COLLECTION);
        for (CheckoutConfirmation confirmation : FakeData.getCheckoutConfirmations()) {
            firebaseContext.checkouts().recordCheckoutConfirmation(confirmation);
        }
    }

    private static void clearCollectionExceptFirstDocument(
            Firestore firestore,
            String collectionName
    ) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = firestore.collection(collectionName)
                .get()
                .get()
                .getDocuments();

        for (int i = 1; i < documents.size(); i++) {
            documents.get(i).getReference().delete().get();
        }
    }
}
