package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FirebaseImplementationTest {

    @Test
    void getBookByIdReturnsExistingBook() {
        FirebaseContext firebase = new FirebaseContext();

        Book book = firebase.getBookById("book-001");

        assertNotNull(book, "Expected book-001 to exist in Firestore");
        assertEquals("book-001", book.getBookId());
        assertEquals("Pride and Prejudice", book.getTitle());
    }

    @Test
    void getBookByIdReturnsNullForMissingBook() {
        FirebaseContext firebase = new FirebaseContext();

        Book book = firebase.getBookById("book-does-not-exist");

        assertNull(book, "Missing book IDs should return null");
    }

    @Test
    void getBookByIdReturnsNullForBlankBookId() {
        FirebaseContext firebase = new FirebaseContext();

        assertNull(firebase.getBookById(null));
        assertNull(firebase.getBookById(""));
        assertNull(firebase.getBookById("   "));
    }
}
