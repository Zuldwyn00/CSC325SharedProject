package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void authenticateUsingLibraryId() {
        AuthService authService = new AuthService();
        FirebaseContext firebase = new FirebaseContext();
        User expectedUser = new User("user-001", "00012345", "alice@example.com", "1234");

        //Check if userID returned is same as expected userID
        assertEquals(expectedUser.getUserId(), authService.authenticate(firebase,"00012345", "1234").getUserId());
    }

    @Test
    void authenticateUsingEmail() {
        AuthService authService = new AuthService();
        FirebaseContext firebase = new FirebaseContext();
        User expectedUser = new User("user-001", "00012345", "alice@example.com", "1234");

        //Check if userID returned is same as expected userID
        assertEquals(expectedUser.getUserId(), authService.authenticate(firebase,"alice@example.com", "1234").getUserId());
    }
}