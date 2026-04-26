package com.csc325.librarymanagementsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import library.data.FirebaseContext;
import library.service.EmailService;
import library.service.NotificationService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FirebaseContext firebase = new FirebaseContext();
        EmailService emailService = new EmailService();
        NotificationService notificationService = new NotificationService(emailService);

        scheduleNotifications(notificationService, firebase);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    private void scheduleNotifications(NotificationService notificationService,
                                       FirebaseContext, firebase) {
        return null;
    }
}
