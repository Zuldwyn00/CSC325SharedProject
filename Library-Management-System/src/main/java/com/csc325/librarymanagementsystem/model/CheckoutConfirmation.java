package com.csc325.librarymanagementsystem.model;

import java.util.Date;
import java.util.List;


public class CheckoutConfirmation {
    private String confirmationNumber;
    private String userId;
    private List<String> bookIds;
    private Date checkoutDate;

    public CheckoutConfirmation() {
    }

    public CheckoutConfirmation(String confirmationNumber, String userId, List<String> bookIds, Date checkoutDate) {
        this.confirmationNumber = confirmationNumber;
        this.userId = userId;
        this.bookIds = bookIds;
        this.checkoutDate = checkoutDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
