package com.csc325.librarymanagementsystem.controller;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.service.Session;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController {
    private final Image defaultBookImage =
            new Image(getClass().getResourceAsStream("/com/csc325/librarymanagementsystem/images/no-image.png"));
    private final FirebaseContext firebaseContext = new FirebaseContext();

    // cached so when changing controller instances the books dont or rehit the firestore every time you reopen the main page
    private static Book cachedBookOfDay;
    private static Book cachedBookOfMonth;
    

    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button loansButton;
    @FXML private Button profileButton;
    @FXML private Button signOutButton;
    @FXML private Button notificationButton;
    @FXML private Label welcomeLabel;
    @FXML private Label bookOfDayTitle;
    @FXML private Label bookOfDayAuthor;
    @FXML private Label bookOfDayGenre;
    @FXML private Label bookOfTheMonthTitle;
    @FXML private Label bookOfTheMonthAuthor;
    @FXML private Label bookOfTheMonthGenre;
    @FXML private ImageView bookOfTheDayImage;
    @FXML private ImageView bookOfTheMonthImage;
    private final java.util.Map<String, Image> imageCache = new java.util.HashMap<>();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getEmail());
        ImageView bookOfTheDayImage1 = bookOfTheDayImage;
        ImageView bookOfTheMonthImage1 = bookOfTheMonthImage;
        bookOfTheDayImage1.setImage(defaultBookImage);
        bookOfTheMonthImage1.setImage(defaultBookImage);

        //this task makes it load in the background and changes it whenenver it gets the books
        Task<Book[]> loadTask = new Task<>() {
            @Override
            protected Book[] call() {
                Book dayBook = getBookOfTheDay();
                Book monthBook = getBookOfTheMonth();
                return new Book[]{dayBook, monthBook};
            }
        };

        loadTask.setOnSucceeded(e -> {


            //calls the task so whenever images and data is ready they are placed into this array
            Book[] books = loadTask.getValue();

            Book bookOfDay = books[0];
            Book bookOfMonth = books[1];

            if (bookOfDay != null) {

                bookOfDayTitle.setText(bookOfDay.getTitle());
                bookOfDayAuthor.setText("Authors: " + bookOfDay.getAuthors());
                bookOfDayGenre.setText("Genres: " + bookOfDay.getGenres());

                loadCoverImageOrPlaceholder(
                        bookOfTheDayImage,
                        bookOfDay.getCoverImageUrl()
                );
            }

            if (bookOfMonth != null) {

                bookOfTheMonthTitle.setText(bookOfMonth.getTitle());
                bookOfTheMonthAuthor.setText("Authors: " + bookOfMonth.getAuthors());
                bookOfTheMonthGenre.setText("Genres: " + bookOfMonth.getGenres());

                loadCoverImageOrPlaceholder(
                        bookOfTheMonthImage,
                        bookOfMonth.getCoverImageUrl()
                );
            }
        });

        new Thread(loadTask).start();

        //actually starts the task
        new Thread(loadTask).start();
    }

    private Book getBookOfTheDay() {

        if (cachedBookOfDay != null) {
            return cachedBookOfDay;
        }

        int bookCollectionSize = firebaseContext.getCollectionSize(FirebaseContext.BOOKS_COLLECTION);
        int positionInCollection = (int) (Math.random() * (bookCollectionSize));

        cachedBookOfDay = firebaseContext.getBookAt(positionInCollection);
        return cachedBookOfDay;
    }

    private Book getBookOfTheMonth() {

        if (cachedBookOfMonth != null) {
            return cachedBookOfMonth;
        }

        Date currentDate = new Date();

        Calendar oneMonthBeforeCurrentCalendar = Calendar.getInstance();
        oneMonthBeforeCurrentCalendar.setTime(currentDate);
        oneMonthBeforeCurrentCalendar.add(Calendar.MONTH, -1);

        Date oneMonthBeforeCurrentDate = oneMonthBeforeCurrentCalendar.getTime();

        List<CheckoutConfirmation> checkoutsPastMonth =
                firebaseContext.checkouts()
                        .getCheckoutConfirmationsBetween(oneMonthBeforeCurrentDate, currentDate);

        // using the list of the past months checkoutconfirmations, count how many times each unique bookId is present within each
        Map<String, Integer> bookIdCounts = new HashMap<>();

        for (CheckoutConfirmation checkout : checkoutsPastMonth) {

            for (String bookId : checkout.getBookIds()) {

                if (!bookIdCounts.containsKey(bookId)) {
                    bookIdCounts.put(bookId, 0);
                }
                bookIdCounts.put(bookId, bookIdCounts.get(bookId) + 1);
            }
        }

        if (bookIdCounts.isEmpty()) {
            return null;
        }

        // Get the highest count bookId from the (bookId, foundCount) key,value HashMap loop above.
        String mostPopularBookId =
                bookIdCounts.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())//compare all key - values with eachother and get max
                        .map(Map.Entry::getKey)// same as a lambda doing "entry -> entry.getKey()"
                        .orElse(null);

        cachedBookOfMonth = firebaseContext.getBookById(mostPopularBookId);
        return cachedBookOfMonth;
    }

    private void loadCoverImageOrPlaceholder(ImageView imageView, String imageUrl) {
        imageView.setImage(defaultBookImage);

        if (imageUrl == null || imageUrl.isBlank()) {
            imageView.setImage(defaultBookImage);
            return;
        }

        if (imageCache.containsKey(imageUrl)) {
            imageView.setImage(imageCache.get(imageUrl));
            return;
        }

        Image image = new Image(imageUrl, true);

        image.progressProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= 1.0) {
                if (!image.isError()) {
                    imageCache.put(imageUrl, image);
                    imageView.setImage(image);
                } else {
                    imageView.setImage(defaultBookImage);
                }
            }
        });
    }

    @FXML
    private void searchButtonOnAction() {
        navigateTo("/com/csc325/librarymanagementsystem/SearchScreen.fxml", searchButton);
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

    @FXML private void onProfileClicked()  {
        navigateTo("/com/csc325/librarymanagementsystem/ProfileScreen.fxml", profileButton);
    }

    @FXML
    private void onSignOutClicked() {
        Session.clear();
        navigateTo("/com/csc325/librarymanagementsystem/LoginScreen.fxml", signOutButton);
    }
    @FXML
    private void onNotificationClicked(){
        navigateTo("/com/csc325/librarymanagementsystem/NotificationScreen.fxml", notificationButton);
    }

    private void navigateTo(String fxmlPath, Button source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene currentScene = source.getScene();
            Scene newScene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
