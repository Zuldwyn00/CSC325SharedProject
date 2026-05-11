package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.model.Book;
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

public class CartController {

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button signOutButton;
    @FXML private Button checkoutButton;

    @FXML private ListView<Book> cartListView;
    @FXML private Label itemCounterLabel;
    @FXML private Button clearbutton;

    @FXML
    public void initialize() {
        cartListView.setCellFactory(listView -> new ListCell<Book>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final HBox row = new HBox(15);
            private final Button RemoveButton = new Button("Remove");

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);

                row.getChildren().addAll(textLabel, imageView, RemoveButton);
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
                RemoveButton.setOnAction(e -> {
                    Session.getCart().removeBook(book.getBookId());
                    row.getChildren().clear();
                    itemCounterLabel.setText("Items: " + Session.getCart().getBooks().size());
                });
                setGraphic(row);
            }
        });

        cartListView.getItems().clear();
        cartListView.getItems().addAll(Session.getCart().getBooks());

        itemCounterLabel.setText("Items: " + Session.getCart().getBooks().size());

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
    @FXML
    private void onCheckoutClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CheckoutScreen.fxml", checkoutButton);
    }
    @FXML
    private void onClearClicked() {
        Session.getCart().clearCart();

        cartListView.getItems().clear();

        itemCounterLabel.setText("Items: " + Session.getCart().getBooks().size());
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
