package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;

public class AuthService {

    // Look up a user by libraryId or email and compare the provided pin against the stored pin
    // returns the User on succes or null if no match or the pin is mismatched as well
    public User authenticate(FirebaseContext firebase, String identifier, String pin) {
        if (firebase == null || identifier == null || pin == null) {
            return null;
        }

        // identifier == (libraryId || email)
        User user = firebase.users().findUserByIdentifier(identifier);
        if (user == null) {
            return null;
        }

        if (pin.equals(user.getLibraryPin())) {
            return user;
        }
        return null;
    }

    public RegisterResult register(FirebaseContext firebase, User user) {
        if (firebase == null || user == null) {
            return RegisterResult.FAILED;
        }

        // prevent creating duplicate accounts on the same libraryId/email.
        if (user.getLibraryId() != null && !user.getLibraryId().isBlank()
                && firebase.users().findUserByIdentifier(user.getLibraryId()) != null) {
            return RegisterResult.USER_ALREADY_EXISTS;
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()
                && firebase.users().findUserByIdentifier(user.getEmail()) != null) {
            return RegisterResult.USER_ALREADY_EXISTS;
        }

        if (firebase.users().recordUser(user)) {
            return RegisterResult.SUCCESS;
        }
        return RegisterResult.FAILED;
    }
}
