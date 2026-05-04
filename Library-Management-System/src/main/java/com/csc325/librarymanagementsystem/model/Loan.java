package com.csc325.librarymanagementsystem.model;

import java.util.Date;

public class Loan {

    private String loanId;
    private String userId;
    private String bookId;
    private Date checkoutDate;
    private Date dueDate;
    private boolean returned;

    public Loan() {
    }

    public Loan(String loanId, String bookId, String userId, Date checkoutDate, Date dueDate, boolean returned) {
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

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
