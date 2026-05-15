module com.csc325.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    requires javafx.graphics;
    requires java.desktop;
    requires java.annotation;

    opens com.csc325.librarymanagementsystem.model to google.cloud.firestore, com.google.api.apicommon;
    opens com.csc325.librarymanagementsystem to javafx.fxml;
    opens com.csc325.librarymanagementsystem.controller to javafx.fxml;
    exports com.csc325.librarymanagementsystem;
}
