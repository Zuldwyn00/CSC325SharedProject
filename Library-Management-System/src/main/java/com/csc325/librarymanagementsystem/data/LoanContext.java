package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.Loan;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoanContext {
    private final String LOANS_COLLECTION = "loans";
    private final Firestore firestore;

    public LoanContext(Firestore firestore) {
        this.firestore = firestore;
    }

    public boolean recordLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("loan cannot be null.");
        }

        DocumentReference loanDocument;
        if (loan.getLoanId() == null || loan.getLoanId().isBlank()) {
            loanDocument = firestore.collection(LOANS_COLLECTION).document();
            loan.setLoanId(loanDocument.getId()); // if the loanId is not directly given, generate it with firestore and set it for the object in memory
        } else {
            loanDocument = firestore.collection(LOANS_COLLECTION).document(loan.getLoanId());
        }

        try {
            loanDocument.create(loan).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error recording loan " + loan.getLoanId() + ": " + e.getMessage());
            return false;
        }
    }

    public List<Loan> getActiveLoans(String userId) {
        // returns a list of active users specific to that userId
        if (userId == null || userId.isBlank()) {return List.of();}

        List<Loan> activeLoans = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("returned", false)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId()); // defensive: stamp doc id in case it wasn't persisted as a field
                activeLoans.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving active loans for " + userId + ": " + e.getMessage());
        }
        return activeLoans;
    }
}
