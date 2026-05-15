package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.model.Notification;
import com.csc325.librarymanagementsystem.service.EmailService;
import com.csc325.librarymanagementsystem.service.NotificationService;
import com.csc325.librarymanagementsystem.service.Session;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class NotificationController {
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button loansButton;
    @FXML private Button profileButton;
    @FXML private Button signOutButton;
    @FXML private Button markAllReadButton;
    @FXML private Button refreshButton;
    @FXML private ListView<String> notificationListView;
    @FXML private Label messageLabel;

    private final NotificationService notificationService =
            new NotificationService(new EmailService());

    @FXML
    public void initialize() {
        loadNotifications();
    }

    private void loadNotifications() {
        notificationListView.getItems().clear();

        if (Session.getCurrentUser() == null) {
            messageLabel.setText("No user is currently logged in.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");

        for (Notification notification : notificationService.getNotificationsForUser(Session.getCurrentUser())) {
            String status = notification.isRead() ? "[Read] " : "[New] ";
            String dateText = notification.getCreatedAt() == null
                    ? ""
                    : " | " + dateFormat.format(notification.getCreatedAt());

            notificationListView.getItems().add(
                    status + notification.getMessage() + dateText
            );
        }

        if (notificationListView.getItems().isEmpty()) {
            messageLabel.setText("No notifications.");
        } else {
            messageLabel.setText("");
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
    private void onCartClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML
    private void onLoansClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/LoanScreen.fxml", loansButton);
    }

    @FXML
    private void onProfileClicked()  {
        navigateTo("/com/csc325/librarymanagementsystem/ProfileScreen.fxml", profileButton);
    }

    @FXML
    private void onSignOutClicked() {
        Session.clear();
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }

    @FXML
    private void onMarkAllReadClicked() {
        notificationService.markAllRead(Session.getCurrentUser());
        loadNotifications();
        messageLabel.setText("All notifications marked as read.");
    }

    @FXML
    private void onRefreshClicked() {
        loadNotifications();
    }

    private void navigateTo(String fxmlPath, Button source){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}