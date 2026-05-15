package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.service.Session;
import com.csc325.librarymanagementsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import com.csc325.librarymanagementsystem.data.LoanContext;
import com.csc325.librarymanagementsystem.data.FirebaseInitializer;

public class ProfileController {
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button signOutButton;
    @FXML private Button loansButton;
    @FXML private Button cartButton;
    @FXML private Button notificationButton;
    @FXML private Label welcomeLabel;
    @FXML private Label profileInitialLabel;
    @FXML private Label userEmail;
    @FXML private Label userLibraryId;
    @FXML private Label userId;
    @FXML private Label activeLoansLabel;

    @FXML void initialize(){
        User user = Session.getCurrentUser();

        welcomeLabel.setText("Welcome, " + user.getEmail());
        userEmail.setText(user.getEmail());
        userLibraryId.setText(user.getLibraryId());
        userId.setText(user.getUserId());

        String initial = user.getEmail().substring(0, 1).toLowerCase();
        profileInitialLabel.setText(initial);

        LoanContext loanContext = new LoanContext(FirebaseInitializer.getFirestore());
        int activeLoans = loanContext.getActiveLoans(user.getUserId()).size();
        activeLoansLabel.setText(String.valueOf(activeLoans));
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

    @FXML
    private void onCartClicked(){
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML
    private void onNotificationClicked() {
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
