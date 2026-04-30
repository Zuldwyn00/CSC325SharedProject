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
    void removeBook_ExistingBookInCart_RemovesFromCart() {
        List<Book> books = FakeData.getBooks();
        Book bookToRemove = books.get(0);
        Book bookToKeep = books.get(1);

        Cart cart = new Cart();
        cart.addBook(bookToRemove);
        cart.addBook(bookToKeep);

        cart.removeBook(bookToRemove.getBookId());

        assertEquals(1, cart.size());
        assertTrue(cart.getBooks().contains(bookToKeep));
        assertFalse(cart.getBooks().contains(bookToRemove));
    }

    @Test
    void removeBook_NotExistingBookInCart_RemovesFromCart() {
        List<Book> books = FakeData.getBooks();

        Cart cart = new Cart();
        cart.addBook(books.get(0));
        cart.addBook(books.get(1));

        String invalidId = "999999999999";

        cart.removeBook(invalidId);

        assertEquals(2, cart.size());
    }
}