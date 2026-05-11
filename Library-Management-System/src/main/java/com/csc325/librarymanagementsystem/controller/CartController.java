package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.service.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CartController {

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button signOutButton;
    @FXML private Button confirmCheckoutButton;

    @FXML private ListView<Book> cartListView;
    @FXML private Label itemCounterLabel;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        setupCartCells();
        refreshCart();
    }

    private void setupCartCells() {
        cartListView.setCellFactory(listView -> new ListCell<Book>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final Button removeButton = new Button("Remove");
            private final HBox row = new HBox(15);

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);

                row.getChildren().addAll(textLabel, imageView, removeButton);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setGraphic(null);
                    return;
                }

                Image image = new Image(book.getCoverImageUrl());
                imageView.setImage(image);

                textLabel.setText(
                        "Title: " + book.getTitle() + "\n" +
                                "Authors: " + book.getAuthors() + "\n" +
                                "Genres: " + book.getGenres() + "\n" +
                                "ISBN: " + book.getIsbn() + "\n" +
                                "Quantity: " + book.getQuantity()
                );

                removeButton.setOnAction(e -> {
                    Session.getCart().removeBook(book.getBookId());
                    refreshCart();
                });

                setGraphic(row);
            }
        });
    }

    private void refreshCart() {
        cartListView.getItems().clear();

        if (Session.getCart() != null) {
            cartListView.getItems().addAll(Session.getCart().getBooks());
            itemCounterLabel.setText("Items: " + Session.getCart().getBooks().size());
            confirmCheckoutButton.setDisable(Session.getCart().getBooks().isEmpty());
        } else {
            itemCounterLabel.setText("Items: 0");
            confirmCheckoutButton.setDisable(true);
        }
    }

    @FXML
    private void onClearClicked() {
        Session.getCart().clearCart();
        messageLabel.setText("");
        refreshCart();
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
            refreshCart();

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