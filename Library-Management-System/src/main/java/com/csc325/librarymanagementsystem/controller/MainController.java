package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
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
    private final FirebaseContext firebaseContext = new FirebaseContext();

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
    @FXML private Label bookOfTheMonthTitle;
    @FXML private Label bookOfTheMonthAuthor;
    @FXML private Label bookOfTheMonthGenre;
    @FXML private ImageView bookOfTheDayImage;
    @FXML private ImageView bookOfTheMonthImage;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getEmail());
        loadBookOfTheDay();
        loadBookOfTheMonthImage();
    }

    private void loadBookOfTheDay() {
        int bookCollectionSize = firebaseContext.getCollectionSize(FirebaseContext.BOOKS_COLLECTION);
        int positionInCollection = (int) (Math.random() * (bookCollectionSize));
        Book bookOfDay = firebaseContext.getBookAt(positionInCollection);

        if (bookOfDay == null) {
            setNoImagePlaceholder(bookOfTheDayImage);
            return;
        }

        bookOfDayTitle.setText(bookOfDay.getTitle());
        bookOfDayAuthor.setText("Authors: " + bookOfDay.getAuthors());
        bookOfDayGenre.setText("Genres: " + bookOfDay.getGenres());

        if (bookOfDay.getCoverImageUrl() == null || bookOfDay.getCoverImageUrl().isBlank()) {
            setNoImagePlaceholder(bookOfTheDayImage);
            return;
        }

        String imageUrl = bookOfDay.getCoverImageUrl();

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

    private void loadBookOfTheMonthImage() {
        String imageUrl = "";

        
        if (imageUrl == null || imageUrl.isBlank()) {
            setNoImagePlaceholder(bookOfTheMonthImage);
            return;
        }
        try {
            Image urlImage = new Image(imageUrl);
            if (urlImage.isError()) {
                setNoImagePlaceholder(bookOfTheMonthImage);
            } else {
                bookOfTheMonthImage.setImage(urlImage);
            }
        } catch (IllegalArgumentException e) {
            setNoImagePlaceholder(bookOfTheMonthImage);
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

    @FXML private void onProfileClicked()  {
        navigateTo("/com/csc325/librarymanagementsystem/ProfileScreen.fxml", profileButton);
    }
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