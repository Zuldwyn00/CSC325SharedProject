module com.csc325.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.csc325.librarymanagementsystem to javafx.fxml;
    opens com.csc325.librarymanagementsystem.controller to javafx.fxml;
    exports com.csc325.librarymanagementsystem;
}