package com.csc325.librarymanagementsystem.model;

import com.csc325.librarymanagementsystem.data.FakeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void addBook() {
        List<Book> books = FakeData.getBooks();

        Cart cart = new Cart();
        cart.addBook(books.get(0));
        cart.addBook(books.get(1));

        assertEquals(2, cart.size());
    }

    @Test
    void removeBook_ExistingBookInCart() {
        List<Book> books = FakeData.getBooks();
        // define the data that is expected for the assert functions later
        Book bookToRemove = books.get(0);
        Book bookToKeep = books.get(1);


        //testing the actual methods
        Cart cart = new Cart();
        cart.addBook(bookToRemove);
        cart.addBook(bookToKeep);

        cart.removeBook(bookToRemove.getBookId());

        // ensuring the pre-defined expected data above asserts to be the same as we expect, thus a successful test.
        assertEquals(1, cart.size()); // 1 is the expected outcome here, while cart.size is the actual current outcome. Ideally these should be the same and come back as a pass
        assertTrue(cart.getBooks().contains(bookToKeep));
        assertFalse(cart.getBooks().contains(bookToRemove));
    }

    @Test
    void removeBook_NotExistingBookInCart() {
        List<Book> books = FakeData.getBooks();

        Cart cart = new Cart();
        cart.addBook(books.get(0));
        cart.addBook(books.get(1));

        String invalidId = "999999999999";

        cart.removeBook(invalidId);

        assertEquals(2, cart.size());
    }
}