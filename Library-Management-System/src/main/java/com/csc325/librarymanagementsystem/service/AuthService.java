package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;

public class AuthService {
    public User authenticate(FirebaseContext firebase, String identifier, String pin) {
        for (User user : FakeData.getUsers()) {
            if (user.getLibraryId().equals(identifier) && user.getLibraryPin().equals(pin)) {
                return user;
            }

            if (user.getEmail().equalsIgnoreCase(identifier) && user.getLibraryPin().equals(pin)) {
                return user;
            }
        }

        return null;
    }
}
