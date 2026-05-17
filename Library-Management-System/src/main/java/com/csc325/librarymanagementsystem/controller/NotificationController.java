package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.model.Notification;
import com.csc325.librarymanagementsystem.service.EmailService;
import com.csc325.librarymanagementsystem.service.NotificationService;
import com.csc325.librarymanagementsystem.service.Session;
import com.csc325.librarymanagementsystem.data.FirebaseContext;
import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

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
    @FXML private Label welcomeLabel;

    private final NotificationService notificationService =
            new NotificationService(new EmailService());

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getEmail());

        notificationService.sendDueSoonAlerts(new FirebaseContext(), 3);
        notificationService.sendOverdueAlerts(new FirebaseContext());

        loadNotifications();
    }
    private void loadNotifications() {
        notificationListView.getItems().clear();

        if (Session.getCurrentUser() == null) {
            messageLabel.setText("No user is currently logged in.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");

        List<Notification> notifications =
                notificationService.getNotificationsForUser(Session.getCurrentUser());

        notifications.sort(Comparator.comparing(
                Notification::getCreatedAt,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));

        Date recentCutoff = new Date(System.currentTimeMillis() - (10 * 60 * 1000));

        List<Notification> newThisSession = new ArrayList<>();
        List<Notification> previousNew = new ArrayList<>();
        List<Notification> readNotifications = new ArrayList<>();

        for (Notification notification : notifications) {
            if (notification.isRead()) {
                readNotifications.add(notification);
            } else if (notification.getCreatedAt() != null &&
                    !notification.getCreatedAt().before(recentCutoff)) {
                newThisSession.add(notification);
            } else {
                previousNew.add(notification);
            }
        }

        addNotificationSection("──────── New this session ────────", newThisSession, dateFormat);
        addNotificationSection("──────── Previous unread ────────", previousNew, dateFormat);
        addNotificationSection("──────── Read notifications ────────", readNotifications, dateFormat);

        if (notificationListView.getItems().isEmpty()) {
            messageLabel.setText("No notifications.");
        } else {
            messageLabel.setText("");
        }
    }
    private void addNotificationSection(String sectionTitle,
                                        List<Notification> notifications,
                                        SimpleDateFormat dateFormat) {
        if (notifications.isEmpty()) {
            return;
        }

        notificationListView.getItems().add(sectionTitle);

        for (Notification notification : notifications) {
            String status = notification.isRead() ? "READ  " : "NEW   ";

            String dateText = notification.getCreatedAt() == null
                    ? "No date"
                    : dateFormat.format(notification.getCreatedAt());

            notificationListView.getItems().add(
                    status + dateText + "\n" +
                            notification.getMessage()
            );
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
        notificationService.sendDueSoonAlerts(new FirebaseContext(), 3);
        notificationService.sendOverdueAlerts(new FirebaseContext());

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