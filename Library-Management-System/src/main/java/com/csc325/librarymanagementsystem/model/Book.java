package com.csc325.librarymanagementsystem.model;
import java.util.list;


public class Book {
    private final String bookId;
    private final String isbn;
    private final String title;
    private final List<String> authors;
    private final List<String> genres;
    private int quantity;

    public Book(String bookId, String isbn, String title,
                List<String> authors, List<String> , int quantity) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
