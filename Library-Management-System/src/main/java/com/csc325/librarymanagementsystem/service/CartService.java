package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Cart;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Loan;
import com.csc325.librarymanagementsystem.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Handles cart and checkout rules for the library system.
// Add books to a cart
// Prevent users from checking out more than 3 books total
// Prevent checkout if a book is unavailable
// Create checkout confirmations

public class CartService {

    private static final int MAX_CHECKOUT_LIMIT = 3;

        // Adds a book to the cart if it is valid, available, not already in cart,
        // and the user will not go over the 3-book checkout limit.

    public boolean addBookToCart(User user, Cart cart, Book book, List<Loan> currentLoans) {
        if (user == null || cart == null || book == null) {
            return false;
        }

        // Book must have at least 1 available copy
        if (book.getQuantity() <= 0) {
            return false;
        }

        // Prevent duplicate books in the same cart
        if (cart.contains(book.getBookId())) {
            return false;
        }

        int activeLoanCount = countActiveLoans(currentLoans);

        // User cannot have more than 3 books total between loans + cart
        if (activeLoanCount + cart.size() >= MAX_CHECKOUT_LIMIT) {
            return false;
        }

        return cart.addBook(book);
    }


    public boolean canCheckout(User user, Cart cart, List<Loan> currentLoans) {
        if (user == null || cart == null || cart.isEmpty()) {
            return false;
        }

        int activeLoanCount = countActiveLoans(currentLoans);

        // Current checked out books + cart books cannot be more than 3
        if (activeLoanCount + cart.size() > MAX_CHECKOUT_LIMIT) {
            return false;
        }

        // Every book in the cart must be available
        for (Book book : cart.getBooks()) {
            if (book == null || book.getQuantity() <= 0) {
                return false;
            }
        }

        return true;
    }

//     Completes checkout and returns a confirmation.
//     This currently updates the local Book quantity.
//     Later, this can be connected to Firebase to save loans/confirmations.

    public CheckoutConfirmation checkout(User user, Cart cart, List<Loan> currentLoans) {
        if (!canCheckout(user, cart, currentLoans)) {
            return null;
        }

        // Decrease inventory for each checked out book
        for (Book book : cart.getBooks()) {
            book.setQuantity(book.getQuantity() - 1);
        }

        // Get all checked out book IDs
        List<String> bookIds = cart.getBooks()
                .stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        // Create confirmation number
        String confirmationNumber = generateConfirmationNumber();

        CheckoutConfirmation confirmation = new CheckoutConfirmation(
                confirmationNumber,
                user.getUserId(),
                bookIds,
                LocalDateTime.now()
        );

        // Empty cart after successful checkout
        cart.clearCart();

        return confirmation;
    }

//     Counts loans that have not been returned yet.

    public int countActiveLoans(List<Loan> currentLoans) {
        if (currentLoans == null) {
            return 0;
        }

        int count = 0;

        for (Loan loan : currentLoans) {
            if (loan != null && !loan.isReturned()) {
                count++;
            }
        }

        return count;
    }

// confirmation number

    public String generateConfirmationNumber() {
        return "CHK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}