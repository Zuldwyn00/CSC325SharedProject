package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Cart;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Loan;
import com.csc325.librarymanagementsystem.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CartService {

    private static final int MAX_CHECKOUT_LIMIT = 3;
    private static final int LOAN_PERIOD_DAYS = 14;

    private final FirebaseContext firebaseContext;

    public CartService() {
        this(new FirebaseContext());
    }

    public CartService(FirebaseContext firebaseContext) {
        this.firebaseContext = firebaseContext;
    }

    public boolean addBookToCart(User user, Cart cart, Book book) {
        if (user == null || cart == null || book == null) {
            return false;
        }

        if (book.getBookId() == null || book.getBookId().isBlank()) {
            return false;
        }

        if (book.getQuantity() <= 0) {
            return false;
        }

        if (cart.contains(book.getBookId())) {
            return false;
        }

        int activeLoanCount = firebaseContext.loans()
                .getActiveLoans(user.getUserId())
                .size();

        if (activeLoanCount + cart.size() >= MAX_CHECKOUT_LIMIT) {
            return false;
        }

        return cart.addBook(book);
    }

    public boolean canCheckout(User user, Cart cart) {
        if (user == null || cart == null || cart.isEmpty()) {
            return false;
        }

        int activeLoanCount = firebaseContext.loans()
                .getActiveLoans(user.getUserId())
                .size();

        if (activeLoanCount + cart.size() > MAX_CHECKOUT_LIMIT) {
            return false;
        }

        for (Book book : cart.getBooks()) {
            if (book == null || book.getBookId() == null || book.getBookId().isBlank()) {
                return false;
            }

            if (book.getQuantity() <= 0) {
                return false;
            }
        }

        return true;
    }

    public CheckoutConfirmation checkout(User user, Cart cart) {
        if (!canCheckout(user, cart)) {
            return null;
        }

        List<String> bookIds = cart.getBooks()
                .stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        Date checkoutDate = new Date();

        CheckoutConfirmation confirmation = new CheckoutConfirmation(
                null,
                user.getUserId(),
                bookIds,
                checkoutDate
        );
        for (Book book : cart.getBooks()) {
            boolean stockUpdated = firebaseContext.adjustStock(book.getBookId(), -1);

            if (!stockUpdated) {
                return null;
            }

            Calendar dueDateCalendar = Calendar.getInstance();
            dueDateCalendar.setTime(checkoutDate);
            dueDateCalendar.add(Calendar.DAY_OF_YEAR, LOAN_PERIOD_DAYS);
            Date dueDate = dueDateCalendar.getTime();

            Loan loan = new Loan(
                    null,
                    book.getBookId(),
                    user.getUserId(),
                    checkoutDate,
                    dueDate,
                    false
            );

            boolean loanRecorded = firebaseContext.loans().recordLoan(loan);

            if (!loanRecorded) {
                return null;
            }
        }

        boolean confirmationRecorded = firebaseContext.checkouts()
                .recordCheckoutConfirmation(confirmation);

        if (!confirmationRecorded) {
            return null;
        }

        cart.clearCart();

        return confirmation;
    }

}