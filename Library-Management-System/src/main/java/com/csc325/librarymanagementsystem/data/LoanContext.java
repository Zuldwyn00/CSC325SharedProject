package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.Loan;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoanContext {
    public static final String LOANS_COLLECTION = "loans";
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

    public List<Loan> getLoansByUserId(String userId) {
        // returns all loans for a specific user, including returned loans
        if (userId == null || userId.isBlank()) {return List.of();}

        List<Loan> userLoans = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId());
                userLoans.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving loans for " + userId + ": " + e.getMessage());
        }
        return userLoans;
    }

    public boolean markLoanReturned(String loanId) {
        if (loanId == null || loanId.isBlank()) {return false;}

        try {
            firestore.collection(LOANS_COLLECTION).document(loanId)
                    .update("returned", true)
                    .get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error marking loan returned " + loanId + ": " + e.getMessage());
            return false;
        }
    }

    // get ALL due soon loans, non-specific to a user. An overload is provided for a specific user as well
    public List<Loan> getDueSoonLoans(int daysAhead) {
        if (daysAhead < 0) {return List.of();}

        // Get current date, and get date that is daysAhead of current date
        Date now = new Date();
        Calendar cutoffCalendar = Calendar.getInstance();
        cutoffCalendar.setTime(now);
        cutoffCalendar.add(Calendar.DAY_OF_YEAR, daysAhead);
        Date cutoff = cutoffCalendar.getTime();

        List<Loan> dueSoon = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("returned", false)
                    .whereGreaterThanOrEqualTo("dueDate", now)
                    .whereLessThanOrEqualTo("dueDate", cutoff)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId());
                dueSoon.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving due-soon loans (" + daysAhead + " days): " + e.getMessage());
        }
        return dueSoon;
    }

    // get due soon loans for a specific user, overloaded method
    public List<Loan> getDueSoonLoans(String userId, int daysAhead) {
        if (userId == null || userId.isBlank() || daysAhead < 0) {return List.of();}

        // Get current date, and get date that is daysAhead of current date
        Date now = new Date();
        Calendar cutoffCalendar = Calendar.getInstance();
        cutoffCalendar.setTime(now);
        cutoffCalendar.add(Calendar.DAY_OF_YEAR, daysAhead);
        Date cutoff = cutoffCalendar.getTime();

        List<Loan> dueSoon = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("returned", false)
                    .whereGreaterThanOrEqualTo("dueDate", now)
                    .whereLessThanOrEqualTo("dueDate", cutoff)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId());
                dueSoon.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving due-soon loans for " + userId
                    + " (" + daysAhead + " days): " + e.getMessage());
        }
        return dueSoon;
    }

    // get ALL overdue loans, non-specific to a user. An overload is provided for a specific user as well
    public List<Loan> getOverdueLoans() {
        Date now = new Date();

        List<Loan> overdue = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("returned", false)
                    .whereLessThan("dueDate", now)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId());
                overdue.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving overdue loans: " + e.getMessage());
        }
        return overdue;
    }

    // get overdue loans for a specific user, overloaded method
    public List<Loan> getOverdueLoans(String userId) {
        if (userId == null || userId.isBlank()) {return List.of();}

        Date now = new Date();

        List<Loan> overdue = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = firestore.collection(LOANS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("returned", false)
                    .whereLessThan("dueDate", now)
                    .get();

            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Loan loan = document.toObject(Loan.class);
                loan.setLoanId(document.getId());
                overdue.add(loan);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving overdue loans for " + userId + ": " + e.getMessage());
        }
        return overdue;
    }

    public Loan getLoanByLoanId(String loanId) {
        if (loanId == null || loanId.isBlank()) {return null;}

        try {
            DocumentSnapshot document = firestore.collection(LOANS_COLLECTION).document(loanId).get().get();
            if (!document.exists()) {
                return null;
            }

            Loan loan = document.toObject(Loan.class);
            assert loan != null;
            loan.setLoanId(document.getId());
            return loan;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving loan " + loanId + ": " + e.getMessage());
        }
        return null;
    }
}
