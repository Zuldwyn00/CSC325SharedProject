package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.model.Cart;
import com.csc325.librarymanagementsystem.model.User;

// Holds data for the current session to be persisted across classes and used.
public final class Session {

    private static User currentUser;
    private static Cart cart;

    private Session() {}

     //Marks the given user as logged in and gives them a fresh empty cart.
     // Call this from LoginController after successful authenticate()

    public static void setCurrentUser(User user) {
        currentUser = user;
        cart = new Cart();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Cart getCart() {
        return cart;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // drops current user and cart, call this whenever using a sign out button.
    public static void clear() {
        currentUser = null;
        cart = null;
    }
}
