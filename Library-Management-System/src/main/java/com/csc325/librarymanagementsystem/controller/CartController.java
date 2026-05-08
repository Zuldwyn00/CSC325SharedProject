package com.csc325.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CartController {

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button signOutButton;

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
