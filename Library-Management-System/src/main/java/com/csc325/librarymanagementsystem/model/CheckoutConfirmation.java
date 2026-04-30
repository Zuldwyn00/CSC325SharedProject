package com.csc325.librarymanagementsystem.model;

import java.time.LocalDateTime;
import java.util.List;

public class CheckoutConfirmation {
    private String confirmationNumber;
    private String userId;
    private List<String> bookIds;
    private LocalDateTime timestamp;

    public CheckoutConfirmation(String confirmationNumber, String userId, List<String> bookIds, LocalDateTime timestamp) {
        this.confirmationNumber = confirmationNumber;
        this.userId = userId;
        this.bookIds = bookIds;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
