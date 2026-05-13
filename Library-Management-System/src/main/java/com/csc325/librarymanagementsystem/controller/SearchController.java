package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.service.SearchType;
import com.csc325.librarymanagementsystem.service.Session;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import java.util.List;

public class SearchController {

    @FXML private TextField SearchTextField;
    @FXML private ChoiceBox<Integer> maxtext;

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
    @FXML private Button checkoutButton;

    private final SearchService searchService = new SearchService();
    private final FirebaseContext firebaseContext = new FirebaseContext();
    private final Image defaultBookImage =
            new Image(getClass().getResourceAsStream("/com/csc325/librarymanagementsystem/images/no-image.png"));
    private final java.util.Map<String, Image> imageCache = new java.util.HashMap<>();

    @FXML
    private void initialize() {
        SearchTypeChoice.getItems().addAll(SearchType.values());
        SearchTypeChoice.setValue(SearchType.TITLE);

        maxtext.getItems().addAll(5, 10);
        maxtext.setValue(5);

        resultsList.setPlaceholder(new Label("Loading books..."));
        searchButton.setDisable(true);

        //This makes it so it loads it in the background
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call()  {
                searchService.loadfirebasedata(firebaseContext);
                return null;
            }
        };
        //hides the search button until its done so that they cant spam click it
        loadTask.setOnSucceeded(e -> {
            searchButton.setDisable(false);
            Label hold = new Label("Search for a book to begin.");
            hold.setStyle("-fx-text-fill: white;");
            resultsList.setPlaceholder(hold);


        });

        loadTask.setOnFailed(e -> {
            searchButton.setDisable(true);
            resultsList.setPlaceholder(new Label("Could not load books. Please try again later."));
            loadTask.getException().printStackTrace();
        });

        //actually starts the task
        new Thread(loadTask).start();

        resultsList.setCellFactory(listView -> new ListCell<Book>() {
            private final ImageView imageView = new ImageView();
            private final Label textLabel = new Label();
            private final Region spacer = new Region();
            private final Button checkoutButton = new Button("Add to Cart");
            private final HBox row = new HBox(15);

            {
                imageView.setImage(defaultBookImage);
                imageView.setFitWidth(80);
                imageView.setFitHeight(115);
                imageView.setPreserveRatio(true);

                textLabel.setWrapText(true);
                textLabel.setPrefWidth(220);

                HBox.setHgrow(spacer, Priority.ALWAYS);

                checkoutButton.setMinWidth(120);
                checkoutButton.setStyle("-fx-text-fill: black;");

                row.getChildren().addAll(imageView, textLabel, spacer, checkoutButton);
            }

            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);

                if (empty || book == null) {
                    imageView.setImage(null);
                    textLabel.setText(null);
                    checkoutButton.setOnAction(null);
                    setGraphic(null);
                    return;
                }

                setGraphic(row);

                imageView.setImage(defaultBookImage);

                String coverUrl = book.getCoverImageUrl();

                if (coverUrl != null && !coverUrl.isBlank()) {

                    //im caching the images so it doesnt refresh when i click on  them
                    if (imageCache.containsKey(coverUrl)) {
                        imageView.setImage(imageCache.get(coverUrl));
                    } else {
                        Image image = new Image(coverUrl, true);

                        image.progressProperty().addListener((obs, oldVal, newVal) -> {
                            if (newVal.doubleValue() >= 1.0 && getItem() == book) {
                                if (!image.isError()) {
                                    imageCache.put(coverUrl, image);
                                    imageView.setImage(image);
                                } else {
                                    imageView.setImage(defaultBookImage);
                                }
                            }
                        });
                    }
                }

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

                boolean inCart = Session.getCart().getBooks().contains(book);

                if (inCart) {
                    checkoutButton.setText("Remove from Cart");
                } else {
                    checkoutButton.setText("Add to Cart");
                }

                checkoutButton.setOnAction(e -> {
                    if (Session.getCart().getBooks().contains(book)) {
                        Session.getCart().removeBook(book.getBookId());
                        checkoutButton.setText("Add to Cart");
                        System.out.println("removed from cart: " + book.getTitle());
                    } else {
                        Session.getCart().addBook(book);
                        checkoutButton.setText("Remove from Cart");
                        System.out.println("added to cart: " + book.getTitle());
                    }
                });
            }
        });
    }

    @FXML
    private void searchButtonOnAction() {

        SearchType type = SearchTypeChoice.getValue();
        String searchText = SearchTextField.getText();

        int max = 5;

        max = maxtext.getValue();

        List<Book> results = searchService.search(searchText, type);

        displayResults(results, max);
    }

    public void displayResults(List<Book> results, int max) {

        resultsList.getItems().clear();

        //no books found if.. well if theres no books found
        if (results == null || results.isEmpty()) {
            Label noResultsLabel = new Label("No books found.");
            noResultsLabel.setStyle("-fx-text-fill: white;");
            resultsList.setPlaceholder(noResultsLabel);

            return;
        }

        resultsList.setItems(FXCollections.observableArrayList(
                results.stream()
                        .limit(max)
                        .toList()
        ));
    }
    @FXML
    private void onCheckoutClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CheckoutScreen.fxml", checkoutButton);
    }
    @FXML
    private void onHomeClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/MainScreen.fxml", homeButton);
    }

    @FXML
    private void onCartClicked() {
        navigateTo("/com/csc325/librarymanagementsystem/CartScreen.fxml", cartButton);
    }

    @FXML
    private void onLoansClicked() {
        // UI-LoansScreen: tu navegacion a LoanScreen
        navigateTo("/com/csc325/librarymanagementsystem/LoanScreen.fxml", loansButton);
    }
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
