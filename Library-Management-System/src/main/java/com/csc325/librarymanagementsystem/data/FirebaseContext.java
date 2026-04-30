package com.csc325.librarymanagementsystem.data;

import java.util.ArrayList;
import java.util.List;
import com.csc325.librarymanagementsystem.data.FakeData;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.model.Loan;

public class FirebaseContext {
    private final String BOOKS_COLLECTION = "books";

    // Fake data, remove later
    private final List<Book> books = new ArrayList<>(FakeData.getBooks());
    private final List<User> users = new ArrayList<>(FakeData.getUsers());
    private final List<Loan> loans = new ArrayList<>(FakeData.getLoans());

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book getBookById(String bookId) {
        if (bookId == null) {return null;}

        for (Book book: books) {
            if (bookId.equals(book.getBookId())) {return book;}
        }
        return null;
    }

    public boolean adjustStock(String bookId, int amount) {
        return false;
    }

    public User findUserByIdentifier(String identifier) {
        if (identifier == null) {return null;}

        for (User user: users) {
            if (identifier.equals(user.getLibraryId())
                || identifier.equals(user.getEmail()))
            {return user;}
        }
        return null;
    }

    public User findUserByUserID(String id) {
        if (id == null) {return null;}

        for (User user: users) {
            if (id.equals(user.getUserId())) {return user;}
        }
        return null;
    }



}
