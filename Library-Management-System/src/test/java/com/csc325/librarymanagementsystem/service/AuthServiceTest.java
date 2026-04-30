package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void authenticateUsingLibraryId() {
        List<User> users = FakeData.getUsers();
        AuthService authService = new AuthService();
        FirebaseContext firebase = new FirebaseContext();

        User expectedUser = users.get(0);
        User actualUser = authService.authenticate(firebase,expectedUser.getLibraryId(), expectedUser.getLibraryPin());

        //Check if userID returned is same as expected userID
        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        assertEquals(expectedUser.getLibraryId(), actualUser.getLibraryId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    void authenticateUsingEmail() {
        List<User> users = FakeData.getUsers();
        AuthService authService = new AuthService();
        FirebaseContext firebase = new FirebaseContext();

        User expectedUser = users.get(0);
        User actualUser = authService.authenticate(firebase, expectedUser.getEmail(), expectedUser.getLibraryPin());

        //Check if userID returned is same as expected userID
        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        assertEquals(expectedUser.getLibraryId(), actualUser.getLibraryId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }
}