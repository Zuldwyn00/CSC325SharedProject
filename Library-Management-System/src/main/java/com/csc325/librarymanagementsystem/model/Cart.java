package com.csc325.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
        if (book == null || books == null) {return false;}
        return books.add(book);
    }

    public boolean removeBook(String bookId) {
        if (bookId == null) {return false;}
        return books.removeIf(book -> book != null && bookId.equals(book.getBookId()));
    }

    public void clearCart() {
        books.clear();
    }

    public int size() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public boolean contains(String bookId) {
        if (bookId == null) {return false;}
        return books.stream().anyMatch(book -> bookId.equals(book.getBookId()));
    }

    public Cart() {
    }

    public Cart(List<Book> books) {
        this.books = Objects.requireNonNullElseGet(books, ArrayList::new);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
