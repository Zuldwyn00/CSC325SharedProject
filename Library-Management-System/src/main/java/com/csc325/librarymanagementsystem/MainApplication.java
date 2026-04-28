package com.csc325.librarymanagementsystem;

import java.io.IOException;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.service.EmailService;
import com.csc325.librarymanagementsystem.service.NotificationService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
                                       FirebaseContext firebase) {
    }
}
