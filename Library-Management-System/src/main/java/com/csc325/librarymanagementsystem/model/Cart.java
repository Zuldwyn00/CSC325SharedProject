package com.csc325.librarymanagementsystem.model;

import java.util.List;

public class Cart {
    private List<Book> books;

    public boolean addBook(Book book) {
        if (book == null || books == null) {return false;}
        return books.add(book);
    }

    public boolean removeBook(String bookId) {
        if (bookId == null || books == null || books.isEmpty()) {return false;}
        return books.removeIf(book -> book != null && book.getBookId().equals(bookId));
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
        if (bookId == null || books == null || books.isEmpty()) {return false;}
        return books.stream().anyMatch(book -> book.getBookId().equals(bookId));
    }

    public Cart(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
