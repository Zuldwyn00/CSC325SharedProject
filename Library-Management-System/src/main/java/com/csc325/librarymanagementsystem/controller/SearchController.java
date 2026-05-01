package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.service.SearchType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class SearchController {

    public javafx.scene.control.TextField SearchTextField;
    @FXML
    private ChoiceBox<SearchType> SearchTypeChoice;

    @FXML
    private Button searchButton;

    @FXML
    private ImageView bookImage;

    @FXML
    private ListView<Book> resultsList;

    @FXML
    private void initialize() {

        SearchTypeChoice.getItems().addAll(SearchType.values());

        SearchTypeChoice.setValue(SearchType.TITLE);

        resultsList.setCellFactory(listView -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final HBox row = new HBox(15);

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);

                row.getChildren().addAll(textLabel, imageView);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image(
                            getClass().getResource("/com/csc325/librarymanagementsystem/images/Minecraft.png").toExternalForm()
                    );

                    imageView.setImage(image);

                    textLabel.setText(
                            "Title: " + book.getTitle() + "\n" +
                                    "Authors: " + book.getAuthors() + "\n" +
                                    "Genres: " + book.getGenres() + "\n" +
                                    "ISBN: " + book.getIsbn() + "\n" +
                                    "Quantity: " + book.getQuantity()
                    );

                    setGraphic(row);
                }
            }
        });
    }
    public void displayResults(List<Book> results) {

        resultsList.getItems().clear();

        if (results == null || results.isEmpty()) {
            return;
        }

        resultsList.getItems().addAll(results);
    }


    @FXML
    private void searchButtonOnAction(javafx.event.ActionEvent  event) {
        SearchType type = SearchTypeChoice.getValue();
        String searchText = SearchTextField.getText();

        SearchService searchService = new SearchService();
        FirebaseContext firebaseContext = new FirebaseContext();

        searchService.loadfirebasedata(firebaseContext);

        List<Book> results = searchService.search(searchText, type);

        displayResults(results);


    }

    @FXML
    private Button backToScreenButton;

    @FXML
    private void onBackToScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/csc325/librarymanagementsystem/MainScreen.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) backToScreenButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
