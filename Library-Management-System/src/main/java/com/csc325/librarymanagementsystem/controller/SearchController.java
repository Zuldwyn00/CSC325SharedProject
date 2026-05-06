package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.service.SearchType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class SearchController {

    @FXML private TextField SearchTextField;
    @FXML private TextField maxtext;

    @FXML private ChoiceBox<SearchType> SearchTypeChoice;
    @FXML private ListView<Book> resultsList;

    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button loansButton;
    @FXML private Button checkoutNavButton;
    @FXML private Button profileButton;
    @FXML private Button settingsButton;
    @FXML private Button signOutButton;
    @FXML private Button notificationButton;

    private final SearchService searchService = new SearchService();
    private final FirebaseContext firebaseContext = new FirebaseContext();

    @FXML
    private void initialize() {
        SearchTypeChoice.getItems().addAll(SearchType.values());
        SearchTypeChoice.setValue(SearchType.TITLE);

        resultsList.setCellFactory(listView -> new ListCell<Book>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final Button checkoutButton = new Button("Check Out");
            private final HBox row = new HBox(15);

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);

                row.getChildren().addAll(textLabel, checkoutButton, imageView);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setGraphic(null);
                    return;
                }

                Image image = new Image(book.getCoverImageUrl());

                imageView.setImage(image);

                textLabel.setText(
                        "Title: " + book.getTitle() + "\n" +
                                "Authors: " + book.getAuthors() + "\n" +
                                "Genres: " + book.getGenres() + "\n" +
                                "ISBN: " + book.getIsbn() + "\n" +
                                "Quantity: " + book.getQuantity()
                );

                boolean available = book.getQuantity() > 0;
                checkoutButton.setVisible(available);
                checkoutButton.setManaged(available);
                checkoutButton.setDisable(!available);

                checkoutButton.setOnAction(e -> {
                    System.out.println("Checkout clicked for: " + book.getTitle());
                });

                setGraphic(row);
            }
        });
    }

    @FXML
    private void searchButtonOnAction() {
        SearchType type = SearchTypeChoice.getValue();
        String searchText = SearchTextField.getText();

        int max = 5;

        try {
            max = Integer.parseInt(maxtext.getText());
        } catch (NumberFormatException e) {
            maxtext.setText("5");
        }

        searchService.loadfirebasedata(firebaseContext);

        List<Book> results = searchService.search(searchText, type);

        displayResults(results, max);
    }

    public void displayResults(List<Book> results, int max) {
        resultsList.getItems().clear();

        if (results == null || results.isEmpty()) {
            return;
        }

        int limit = Math.min(max, results.size());

        for (int i = 0; i < limit; i++) {
            resultsList.getItems().add(results.get(i));
        }
    }

    @FXML
    private void onHomeClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/MainScreen.fxml", homeButton);
    }

    @FXML
    private void onCartClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML private void onLoansClicked()    { System.out.println("Loans clicked"); }
    @FXML private void onCheckoutClicked() { System.out.println("Checkout clicked"); }
    @FXML private void onProfileClicked()  { System.out.println("Profile clicked"); }
    @FXML private void onSettingsClicked() { System.out.println("Settings clicked"); }

    @FXML
    private void onSignOutClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }


    private void navigateTo(String fxmlPath, Button source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();
            Scene currentScene = source.getScene();

            Scene newScene = new Scene(
                    root,
                    currentScene.getWidth(),
                    currentScene.getHeight()
            );

            stage.setScene(newScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}