package com.csc325.librarymanagementsystem.model;

public class User {


    /*
     In a production environment, we would store a pin hash and pin salt with a user and NOT store their
     libraryPin and we would need a class to handle hashing functions, but for this project its unnecessary
     and a bit out of scope within the timeframe for now.
    */
    private String userId;
    private String libraryId;
    private String email;
    private String libraryPin;

    public User() {
    }

    public User(String userId, String libraryId, String email, String libraryPin) {
        this.userId = userId;
        this.libraryId = libraryId;
        this.email = email;
        this.libraryPin = libraryPin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLibraryPin() {
        return libraryPin;
    }

    public void setLibraryPin(String libraryPin) {
        this.libraryPin = libraryPin;
    }
}
