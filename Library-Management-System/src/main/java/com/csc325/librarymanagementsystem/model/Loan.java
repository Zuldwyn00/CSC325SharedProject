package com.csc325.librarymanagementsystem.model;

import java.time.LocalDate;


/**
 * A Loan object represents a single instance of a checked-out book loan for a user.
 */

public class Loan {

    private final String loanId;
    private final String userId;
    private final String bookId;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private boolean returned;

    public Loan(String loanId, String bookId, String userId, LocalDate checkoutDate, LocalDate dueDate, boolean returned) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.userId = userId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}