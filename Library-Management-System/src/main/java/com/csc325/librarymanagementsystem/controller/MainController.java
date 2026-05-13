package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.service.Session;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {
    private static final String NO_IMAGE_RESOURCE = "/com/csc325/librarymanagementsystem/images/no-image.png";

    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button loansButton;
    @FXML private Button checkoutButton;
    @FXML private Button profileButton;
    @FXML private Button settingsButton;
    @FXML private Button signOutButton;
    @FXML private Button notificationButton;
    @FXML private Label welcomeLabel;
    @FXML private Label bookOfDayTitle;
    @FXML private Label bookOfDayAuthor;
    @FXML private Label bookOfDayGenre;
    @FXML private Label authorOfTheMonthTitle;
    @FXML private Label authorOfTheMonthAuthor;
    @FXML private Label authorOfTheMonthGenre;
    @FXML private ImageView bookOfTheDayImage;
    @FXML private ImageView authorOfTheMonthImage;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getEmail());
        loadBookOfTheDayImage();
        loadAuthorOfTheMonthImage();
    }

    private void loadBookOfTheDayImage() {
        String imageUrl = "";
        if (imageUrl == null || imageUrl.isBlank()) {
            setNoImagePlaceholder(bookOfTheDayImage);
            return;
        }
        try {
            Image urlImage = new Image(imageUrl);
            if (urlImage.isError()) {
                setNoImagePlaceholder(bookOfTheDayImage);
            } else {
                bookOfTheDayImage.setImage(urlImage);
            }
        } catch (IllegalArgumentException e) {
            setNoImagePlaceholder(bookOfTheDayImage);
        }
    }

    private void loadAuthorOfTheMonthImage() {
        String imageUrl = "";
        if (imageUrl == null || imageUrl.isBlank()) {
            setNoImagePlaceholder(authorOfTheMonthImage);
            return;
        }
        try {
            Image urlImage = new Image(imageUrl);
            if (urlImage.isError()) {
                setNoImagePlaceholder(authorOfTheMonthImage);
            } else {
                authorOfTheMonthImage.setImage(urlImage);
            }
        } catch (IllegalArgumentException e) {
            setNoImagePlaceholder(authorOfTheMonthImage);
        }
    }

    private void setNoImagePlaceholder(ImageView imageView) {
        URL noImageResource = getClass().getResource(NO_IMAGE_RESOURCE);
        if (noImageResource == null) {
            imageView.setImage(null);
            return;
        }
        imageView.setImage(new Image(noImageResource.toExternalForm()));
    }

    @FXML
    private void searchButtonOnAction() {
        navigateTo("/com/csc325/librarymanagementsystem/SearchScreen.fxml", searchButton);
    }

    @FXML
    private void onCartClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML
    private void onLoansClicked() {
        // UI-LoansScreen: tu navegacion a LoanScreen
        navigateTo("/com/csc325/librarymanagementsystem/LoanScreen.fxml", loansButton);
    }

    @FXML
    private void onCheckoutClicked() {
        // main: navegacion a CheckoutScreen
        navigateTo("/com/csc325/librarymanagementsystem/CheckoutScreen.fxml", checkoutButton);
    }

    @FXML private void onProfileClicked()  { System.out.println("Profile clicked"); }
    @FXML private void onSettingsClicked() { System.out.println("Settings clicked"); }

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
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}