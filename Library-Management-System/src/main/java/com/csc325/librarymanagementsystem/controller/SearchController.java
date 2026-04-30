package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.service.SearchType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SearchController {

    public javafx.scene.control.TextField SearchTextField;
    @FXML
    private ChoiceBox<String> SearchTypeChoice;

    @FXML
    private ListView<String> resultsList;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {

        SearchTypeChoice.getItems().addAll(
                "Title",
                "Author",
                "Genre",
                "ISBN"
        );

        // optional: set default value
        SearchTypeChoice.setValue("Title");
    }
    public void displayResults(List<Book> results) {

        resultsList.getItems().clear();

        for (Book book : results) {

            String item =
                    "Title: " + book.getTitle() + "\n" +
                            "Authors: " + book.getAuthors() + "\n" +
                            "Genres: " + book.getGenres() + "\n" +
                            "ISBN: " + book.getIsbn() + "\n" +
                            "Quantity: " + book.getQuantity();

            resultsList.getItems().add(item);
        }
    }


    @FXML
    private void searchButtonOnAction(javafx.event.ActionEvent  event) {
        String type = SearchTypeChoice.getValue();
        String searchText = SearchTextField.getText();

        SearchService searchService = new SearchService();
        FirebaseContext firebaseContext = new FirebaseContext();

        searchService.loadfirebasedata(firebaseContext);

        List<Book> results = searchService.search(searchText, type);

        displayResults(results);


    }


}
