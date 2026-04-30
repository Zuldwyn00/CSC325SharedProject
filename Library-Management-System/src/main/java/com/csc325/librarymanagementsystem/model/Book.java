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

    public String getCoverImageUrl() {
        return coverImageUrl;
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


