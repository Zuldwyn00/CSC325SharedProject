package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.service.AuthService;
import com.csc325.librarymanagementsystem.service.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField userPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessage;

    private AuthService authService;

    @FXML
    private void initialize() {
        authService = new AuthService();

        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String identifier = username.getText().trim();
        String pin = userPassword.getText().trim();

        if (identifier.isEmpty() || pin.isEmpty()) {
            errorMessage.setText("Please enter username and password.");
            return;
        }

        User user = authService.authenticate(new FirebaseContext(), identifier, pin);

        if (user != null) {
            Session.setCurrentUser(user);

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/csc325/librarymanagementsystem/MainScreen.fxml")
                );

                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.setText("Could not load main screen.");
            }
        } else {
            errorMessage.setText("Invalid login information.");
        }
    }
}