package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.CheckoutContext;
import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.service.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutController {

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button signOutButton;
    @FXML private Button confirmCheckoutButton;


    @FXML private ListView<String> checkoutListView;
    @FXML private Label cartSummaryLabel;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        loadCartItems();
    }

    private void loadCartItems() {
        checkoutListView.getItems().clear();

        List<Book> books = Session.getCart().getBooks();

        for (Book book : books) {
            checkoutListView.getItems().add(
                    book.getTitle() + " | ISBN: " + book.getIsbn()
            );
        }

        cartSummaryLabel.setText("Books selected: " + books.size());

        if (books.isEmpty()) {
            messageLabel.setText("Your cart is empty.");
            confirmCheckoutButton.setDisable(true);
        } else {
            messageLabel.setText("");
            confirmCheckoutButton.setDisable(false);
        }
    }

    @FXML
    private void onConfirmCheckoutClicked() {

        List<Book> books = Session.getCart().getBooks();

        if (books == null || books.isEmpty()) {
            messageLabel.setText("Your cart is empty.");
            return;
        }

        List<String> bookIds = books.stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        String userId = Session.getCurrentUser().getUserId();

        CheckoutConfirmation confirmation = new CheckoutConfirmation(
                null,
                userId,
                bookIds,
                new Date()
        );

        FirebaseContext firebaseContext = new FirebaseContext();

        boolean success = firebaseContext
                .checkouts()
                .recordCheckoutConfirmation(confirmation);

        if (success) {

            Session.getCart().clearCart();

            checkoutListView.getItems().clear();

            cartSummaryLabel.setText("Books selected: 0");

            messageLabel.setText(
                    "Checkout complete! Confirmation #: "
                            + confirmation.getConfirmationNumber()
            );

        } else {

            messageLabel.setText("Checkout failed.");
        }
    }

    @FXML
    private void onHomeClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/MainScreen.fxml", homeButton);
    }

    @FXML
    private void onSearchClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/SearchScreen.fxml", searchButton);
    }

    @FXML
    private void onCartClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML
    private void onSignOutClicked() {
        Session.clear();
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }

    private void navigateTo(String fxmlPath, Button source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();
            Scene currentScene = source.getScene();

            Scene newScene = new Scene(
                    root,
                    currentScene.getWidth(),
                    currentScene.getHeight()
            );

            stage.setScene(newScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}