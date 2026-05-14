package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Loan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class LoanController {
    @FXML
    private Button homeButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label loanCounterLabel;
    @FXML
    private ListView<Loan> loanListView;

    private FirebaseContext firebase;

    @FXML private Button profileButton;

    @FXML
    private void initialize(){
        firebase = new FirebaseContext();

        loanListView.setCellFactory(listView -> new ListCell<>(){
            @Override
            protected void updateItem(Loan loan, boolean empty){
                super.updateItem(loan, empty);
                if (empty || loan == null){
                    setText(null);
                } else {
                    String status = loan.isReturned() ? "Returned" : "Active";
                    setText(
                            "Book ID: " + loan.getBookId() + "\n" +
                            "Checked out: " + loan.getCheckoutDate() + "\n" +
                            "Due: " + loan.getDueDate() + "\n" +
                            "status: " + status
                    );

                }
            }

        });
    }

    public void loadLoans(String userId){
        List<Loan> loans = firebase.loans().getActiveLoans(userId);
        loanListView.getItems().setAll(loans);
        loanCounterLabel.setText(loans.size() + "Active Loan" + (loans.size() == 1 ? "" : "s"));
    }

    public void setWelcomeLabel(String name){
        welcomeLabel.setText("Welcome, " + name + "!");
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
    private void onSignOutClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }

    @FXML private void onProfileClicked()  {
        navigateTo("/com/csc325/librarymanagementsystem/ProfileScreen.fxml", profileButton);
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
