package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.service.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

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
    private void onMarkAllReadClicked(){
        System.out.println("Mark All Read Clicked");
    }

    @FXML
    private void onRefreshClicked(){
        System.out.println("Refresh Clicked");
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
