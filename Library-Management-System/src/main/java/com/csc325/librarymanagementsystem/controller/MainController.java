package com.csc325.librarymanagementsystem.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button debugbutton;

    @FXML
    private void ondebug() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/csc325/librarymanagementsystem/SearchScreen.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) debugbutton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
