package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseContextTest {

    @Test
    void getAllBooks() {
        FirebaseContext firebase = new FirebaseContext();

        List<Book> books = firebase.getAllBooks();

        // Basic assertion to ensure call worked
        assertNotNull(books, "Book list should not be null");

        // Print all books
        if (books.isEmpty()) {
            System.out.println("No books found in Firebase.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}