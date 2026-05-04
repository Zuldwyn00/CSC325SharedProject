package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Cart;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Loan;
import com.csc325.librarymanagementsystem.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private final CartService cartService = new CartService();

    private User makeUser() {
        return new User("user1", "lib123", "student@test.com", "1234");
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

    private Loan makeActiveLoan(String loanId, String bookId) {
        return new Loan(
                loanId,
                bookId,
                "user1",
                LocalDate.now(),
                LocalDate.now().plusWeeks(2),
                false
        );
    }

    private Loan makeReturnedLoan(String loanId, String bookId) {
        return new Loan(
                loanId,
                bookId,
                "user1",
                LocalDate.now(),
                LocalDate.now().plusWeeks(2),
                true
        );
    }

    @Test
    void addBookToCartShouldAddAvailableBook() {
        User user = makeUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 2);
        List<Loan> currentLoans = new ArrayList<>();

        boolean result = cartService.addBookToCart(user, cart, book, currentLoans);

        assertTrue(result);
        assertEquals(1, cart.size());
        assertTrue(cart.contains("book1"));
    }

    @Test
    void addBookToCartShouldRejectUnavailableBook() {
        User user = makeUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 0);
        List<Loan> currentLoans = new ArrayList<>();

        boolean result = cartService.addBookToCart(user, cart, book, currentLoans);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void addBookToCartShouldRejectDuplicateBook() {
        User user = makeUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 2);
        List<Loan> currentLoans = new ArrayList<>();

        boolean firstAdd = cartService.addBookToCart(user, cart, book, currentLoans);
        boolean secondAdd = cartService.addBookToCart(user, cart, book, currentLoans);

        assertTrue(firstAdd);
        assertFalse(secondAdd);
        assertEquals(1, cart.size());
    }

    @Test
    void addBookToCartShouldRejectBookWhenUserAlreadyAtLimit() {
        User user = makeUser();
        Cart cart = new Cart();
        Book book = makeBook("book4", 2);

        List<Loan> currentLoans = List.of(
                makeActiveLoan("loan1", "book1"),
                makeActiveLoan("loan2", "book2"),
                makeActiveLoan("loan3", "book3")
        );

        boolean result = cartService.addBookToCart(user, cart, book, currentLoans);

        assertFalse(result);
        assertEquals(0, cart.size());
    }

    @Test
    void canCheckoutShouldReturnFalseForEmptyCart() {
        User user = makeUser();
        Cart cart = new Cart();
        List<Loan> currentLoans = new ArrayList<>();

        boolean result = cartService.canCheckout(user, cart, currentLoans);

        assertFalse(result);
    }

    @Test
    void canCheckoutShouldReturnFalseWhenCheckoutLimitExceeded() {
        User user = makeUser();
        Cart cart = new Cart();

        cart.addBook(makeBook("book3", 1));
        cart.addBook(makeBook("book4", 1));

        List<Loan> currentLoans = List.of(
                makeActiveLoan("loan1", "book1"),
                makeActiveLoan("loan2", "book2")
        );

        boolean result = cartService.canCheckout(user, cart, currentLoans);

        assertFalse(result);
    }

    @Test
    void canCheckoutShouldIgnoreReturnedLoans() {
        User user = makeUser();
        Cart cart = new Cart();

        cart.addBook(makeBook("book1", 1));
        cart.addBook(makeBook("book2", 1));
        cart.addBook(makeBook("book3", 1));

        List<Loan> currentLoans = List.of(
                makeReturnedLoan("loan1", "oldBook")
        );

        boolean result = cartService.canCheckout(user, cart, currentLoans);

        assertTrue(result);
    }

    @Test
    void checkoutShouldDecreaseBookQuantity() {
        User user = makeUser();
        Cart cart = new Cart();
        Book book = makeBook("book1", 3);
        cart.addBook(book);

        CheckoutConfirmation confirmation = cartService.checkout(user, cart, new ArrayList<>());

        assertNotNull(confirmation);
        assertEquals(2, book.getQuantity());
    }

    @Test
    void checkoutShouldClearCartAfterSuccessfulCheckout() {
        User user = makeUser();
        Cart cart = new Cart();
        cart.addBook(makeBook("book1", 2));
        cart.addBook(makeBook("book2", 2));

        CheckoutConfirmation confirmation = cartService.checkout(user, cart, new ArrayList<>());

        assertNotNull(confirmation);
        assertTrue(cart.isEmpty());
    }

    @Test
    void checkoutShouldCreateConfirmationNumber() {
        User user = makeUser();
        Cart cart = new Cart();
        cart.addBook(makeBook("book1", 2));

        CheckoutConfirmation confirmation = cartService.checkout(user, cart, new ArrayList<>());

        assertNotNull(confirmation);
        assertNotNull(confirmation.getConfirmationNumber());
        assertTrue(confirmation.getConfirmationNumber().startsWith("CHK-"));
        assertEquals("user1", confirmation.getUserId());
        assertEquals(1, confirmation.getBookIds().size());
        assertEquals("book1", confirmation.getBookIds().get(0));
    }

    @Test
    void checkoutShouldReturnNullIfCheckoutIsInvalid() {
        User user = makeUser();
        Cart cart = new Cart();

        CheckoutConfirmation confirmation = cartService.checkout(user, cart, new ArrayList<>());

        assertNull(confirmation);
    }

    @Test
    void countActiveLoansShouldOnlyCountUnreturnedLoans() {
        List<Loan> loans = List.of(
                makeActiveLoan("loan1", "book1"),
                makeReturnedLoan("loan2", "book2"),
                makeActiveLoan("loan3", "book3")
        );

        int activeLoans = cartService.countActiveLoans(loans);

        assertEquals(2, activeLoans);
    }
}