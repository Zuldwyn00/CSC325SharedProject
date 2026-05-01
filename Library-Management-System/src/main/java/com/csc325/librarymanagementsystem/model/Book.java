package com.csc325.librarymanagementsystem.model;

import java.util.List;

public class Book {
    private String bookId;
    private String isbn;
    private String title;
    private List<String> authors;
    private List<String> genres;
    private String coverImageUrl;
    private int quantity;

    public Book() {
    }

    public Book(String bookId, String isbn, String title,
                List<String> authors, List<String> genres, String coverImageUrl, int quantity) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.coverImageUrl = coverImageUrl;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
