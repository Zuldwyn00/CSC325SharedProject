package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import java.util.concurrent.ExecutionException;

public class CheckoutContext {
    public static final String CHECKOUTS_COLLECTION = "checkout";

    private final Firestore firestore;

    public CheckoutContext(Firestore firestore) {
        this.firestore = firestore;
    }

    public boolean recordCheckoutConfirmation(CheckoutConfirmation confirmation) {
        if (confirmation == null) {
            throw new IllegalArgumentException("confirmation cannot be null.");
        }

        DocumentReference checkoutRef;
        if (confirmation.getConfirmationNumber() == null || confirmation.getConfirmationNumber().isBlank()) {
            checkoutRef = firestore.collection(CHECKOUTS_COLLECTION).document();
            confirmation.setConfirmationNumber(checkoutRef.getId()); // if the checkoutId is not directly given, generate it with firestore and set it for the object in memory
        } else {
            checkoutRef = firestore.collection(CHECKOUTS_COLLECTION).document(confirmation.getConfirmationNumber());
        }

        try {
            checkoutRef.create(confirmation).get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error recording checkout for: " + checkoutRef.getId() + " - " + e.getMessage());
            return false;
        }
    }

    public CheckoutConfirmation getCheckoutConfirmation(String confirmationNumber) {
        if (confirmationNumber == null || confirmationNumber.isBlank()) {return null;}

        try {
            DocumentSnapshot snapshot = firestore.collection(CHECKOUTS_COLLECTION)
                                                 .document(confirmationNumber)
                                                 .get()
                                                 .get();
            if (!snapshot.exists()) {
                return null;
            }
            CheckoutConfirmation confirmation = snapshot.toObject(CheckoutConfirmation.class);
            if (confirmation != null) {
                confirmation.setConfirmationNumber(snapshot.getId());
            }
            return confirmation;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving checkout confirmation " + confirmationNumber + ": " + e.getMessage());
            return null;
        }
    }
}
