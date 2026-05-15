package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.service.AuthService;
import com.csc325.librarymanagementsystem.service.RegisterResult;
import com.csc325.librarymanagementsystem.service.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField userPassword;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label errorMessage;

    private AuthService authService;

    @FXML
    private void initialize() {
        authService = new AuthService();

        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
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
                Scene currentScene = loginButton.getScene();
                stage.setScene(new Scene(root, currentScene.getWidth(), currentScene.getHeight()));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.setText("Could not load main screen.");
            }
        } else {
            errorMessage.setText("Invalid login information.");
        }
    }

    private void handleRegister() {
        String identifier = username.getText().trim();
        String pin = userPassword.getText().trim();

        // #1. Validate input
        String validationError = getValidationError(identifier, pin);
        if (validationError != null) {
            errorMessage.setText(validationError);
            return;
        }

        // #2. Create user object
        User userToRegister = new User();
        userToRegister.setEmail(identifier);
        userToRegister.setLibraryPin(pin);

        // #3. Attempt to register user
        RegisterResult result = authService.register(new FirebaseContext(), userToRegister);

        // #4. Handle result
        switch (result) {
            case SUCCESS -> errorMessage.setText("Successfully registered user, please attempt to login.");
            case USER_ALREADY_EXISTS -> errorMessage.setText("User already exists.");
            case FAILED -> errorMessage.setText("Registration failed. Please try again.");
        }
    }

    private String getValidationError(String email, String pin) {
        if (email.isEmpty() || pin.isEmpty()) {
            return "Email and password cannot be empty.";
        }
        if (pin.length() < 4 || pin.length() > 12) {
            return "PIN must be between 4 and 12 characters.";
        }
        // Accepts @{domain}.com/.org/.net/.edu etc...
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Please enter a valid email address.";
        }

        return null; // Return null if there are no errors
    }

}