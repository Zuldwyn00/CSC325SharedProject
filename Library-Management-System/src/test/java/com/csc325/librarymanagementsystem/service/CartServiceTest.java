package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Cart;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private final CartService cartService = new CartService();

    private User getTestUser() {
        return FakeData.getUsers().get(0);
    }

    private Book makeBook(String bookId, int quantity) {
        return new Book(
                bookId,
                "isbn-" + bookId,
                "Test Book " + bookId,
                List.of("Test Author"),
                List.of("Test Genre"),
                "",
                quantity
        );
    }

    @Test
    void addBookToCartShouldRejectNullUser() {
        Cart cart = new Cart();
        Book book = makeBook("book1", 2);

        boolean result = cartService.addBookToCart(null, cart, book);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void addBookToCartShouldRejectNullCart() {
        User user = getTestUser();
        Book book = makeBook("book1", 2);

        boolean result = cartService.addBookToCart(user, null, book);

        assertFalse(result);
    }

    @Test
    void addBookToCartShouldRejectNullBook() {
        User user = getTestUser();
        Cart cart = new Cart();

        boolean result = cartService.addBookToCart(user, cart, null);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void addBookToCartShouldRejectBookWithBlankId() {
        User user = getTestUser();
        Cart cart = new Cart();
        Book book = makeBook("", 2);

        boolean result = cartService.addBookToCart(user, cart, book);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void addBookToCartShouldRejectUnavailableBook() {
        User user = getTestUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 0);

        boolean result = cartService.addBookToCart(user, cart, book);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void addBookToCartShouldRejectDuplicateBook() {
        User user = getTestUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 2);

        cart.addBook(book);

        boolean result = cartService.addBookToCart(user, cart, book);

        assertFalse(result);
        assertEquals(1, cart.size());
    }

    @Test
    void canCheckoutShouldReturnFalseForNullUser() {
        Cart cart = new Cart();
        cart.addBook(makeBook("book1", 2));

        boolean result = cartService.canCheckout(null, cart);

        assertFalse(result);
    }

    @Test
    void canCheckoutShouldReturnFalseForNullCart() {
        User user = getTestUser();

        boolean result = cartService.canCheckout(user, null);

        assertFalse(result);
    }

    @Test
    void canCheckoutShouldReturnFalseForEmptyCart() {
        User user = getTestUser();
        Cart cart = new Cart();

        boolean result = cartService.canCheckout(user, cart);

        assertFalse(result);
    }

    @Test
    void checkoutShouldReturnNullForEmptyCart() {
        User user = getTestUser();
        Cart cart = new Cart();

        CheckoutConfirmation confirmation = cartService.checkout(user, cart);

        assertNull(confirmation);
    }

}