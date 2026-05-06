package com.csc325.librarymanagementsystem.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class CartController {

    @FXML
    private Button homeButton;
    private Button searchButton;



    @FXML
    private void onHomeClicked(){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/csc325/librarymanagementsystem/MainScreen.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
