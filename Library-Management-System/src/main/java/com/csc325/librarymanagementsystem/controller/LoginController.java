package com.csc325.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField userPassword;

    @FXML
    private Button loginButton;

    @FXML Label errorMessage;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            if (!username.getText().isEmpty() && !userPassword.getText().isEmpty()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csc325/librarymanagementsystem/MainScreen.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorMessage.setText("Please enter correct fields");
            }
        });
    }
}
