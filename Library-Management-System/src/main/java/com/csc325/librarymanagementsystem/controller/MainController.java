package com.csc325.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {

    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button loansButton;
    @FXML private Button checkoutButton;
    @FXML private Button profileButton;
    @FXML private Button settingsButton;
    @FXML private Button signOutButton;
    @FXML private Button notificationButton;
    @FXML private Label welcomeLabel;
    @FXML private Label mostLoanedTitle;
    @FXML private Label mostLoanedAuthor;
    @FXML private Label mostLoanedCount;
    @FXML private Label bookOfDayTitle;
    @FXML private Label bookOfDayAuthor;
    @FXML private Label bookOfDayGenre;
    @FXML private ImageView mostLoanedImage;
    @FXML private ImageView bookOfDayImage;

    @FXML
    private void searchButtonOnAction() {
        navigateTo("/com/csc325/librarymanagementsystem/SearchScreen.fxml", searchButton);
    }

    @FXML private void onCartClicked()     { System.out.println("Cart clicked"); }
    @FXML private void onLoansClicked()    { System.out.println("Loans clicked"); }
    @FXML private void onCheckoutClicked() { System.out.println("Checkout clicked"); }
    @FXML private void onProfileClicked()  { System.out.println("Profile clicked"); }
    @FXML private void onSettingsClicked() { System.out.println("Settings clicked"); }

    @FXML
    private void onSignOutClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }

    private void navigateTo(String fxmlPath, Button source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}