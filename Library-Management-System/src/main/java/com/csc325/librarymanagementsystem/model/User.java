package com.csc325.librarymanagementsystem.model;

/**
 * represents a library user/patrons account
 */

public class User {

    private final String userId;
    private final String libraryId;
    private final String email;
    private final String libraryPin;

    public User(String userId, String libraryId, String email, String libraryPin) {
        this.userId = userId;
        this.libraryId = libraryId;
        this.email = email;
        this.libraryPin = libraryPin;
    }

    public String getUserId() {
        return userId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public String getEmail() {
        return email;
    }

    public String getLibraryPin() {
        return libraryPin;
    }
}
