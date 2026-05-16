package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.exception.CheckoutException;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.service.CartService;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.service.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
//hi

public class CartController {

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button signOutButton;
    @FXML private Button confirmCheckoutButton;

    @FXML private ListView<Book> cartListView;
    @FXML private Label itemCounterLabel;
    @FXML private Label messageLabel;
    @FXML private Button loansButton;
    @FXML private Button profileButton;
    @FXML private Button notificationButton;
    @FXML private Label welcomeLabel;

    private final CartService cartService = new CartService();
    private final Image defaultBookImage =
            new Image(getClass().getResourceAsStream("/com/csc325/librarymanagementsystem/images/no-image.png"));
    private final java.util.Map<String, Image> imageCache = new java.util.HashMap<>();

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getEmail());
        setupCartCells();
        refreshCart();
    }

    private void setupCartCells() {
        cartListView.setCellFactory(listView -> new ListCell<Book>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final Region spacer = new Region();
            private final Button removeButton = new Button("Remove");
            private final HBox row = new HBox(15);

            {
                imageView.setImage(defaultBookImage);
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);
                textLabel.setPrefWidth(220);

                HBox.setHgrow(spacer, Priority.ALWAYS);

                removeButton.setMinWidth(100);
                removeButton.setStyle("-fx-text-fill: black;");

                row.getChildren().addAll(imageView, textLabel, spacer, removeButton);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    imageView.setImage(null);
                    textLabel.setText(null);
                    removeButton.setOnAction(null);
                    setGraphic(null);
                    return;
                }

                setGraphic(row);

                imageView.setImage(defaultBookImage);

                String coverUrl = book.getCoverImageUrl();

                if (coverUrl != null && !coverUrl.isBlank()) {

                    if (imageCache.containsKey(coverUrl)) {
                        imageView.setImage(imageCache.get(coverUrl));
                    } else {
                        Image image = new Image(coverUrl, true);

                        image.progressProperty().addListener((obs, oldVal, newVal) -> {
                            if (newVal.doubleValue() >= 1.0 && getItem() == book) {
                                if (!image.isError()) {
                                    imageCache.put(coverUrl, image);
                                    imageView.setImage(image);
                                } else {
                                    imageView.setImage(defaultBookImage);
                                }
                            }
                        });
                    }
                }

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

        try {

            if (Session.getCart() == null || Session.getCart().isEmpty()) {
                messageLabel.setText("Your cart is empty.");
                return;
            }

            CheckoutConfirmation confirmation = cartService.checkout(
                    Session.getCurrentUser(),
                    Session.getCart()
            );

            refreshCart();

            messageLabel.setText(
                    "Checkout complete! Confirmation #: "
                            + confirmation.getConfirmationNumber()
            );

        } catch (CheckoutException e) {

            messageLabel.setText(e.getMessage());

        } catch (Exception e) {

            messageLabel.setText("Checkout failed. Please try again.");
            e.printStackTrace();
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

    @FXML
    private void onLoansClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/LoanScreen.fxml", loansButton);
    }

    @FXML private void onProfileClicked()  {
        navigateTo("/com/csc325/librarymanagementsystem/ProfileScreen.fxml", profileButton);
    }

    @FXML
    private void onNotificationClicked(){
        navigateTo("/com/csc325/librarymanagementsystem/NotificationScreen.fxml", notificationButton);
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