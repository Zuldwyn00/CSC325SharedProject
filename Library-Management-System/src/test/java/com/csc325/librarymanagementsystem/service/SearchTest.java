package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;

import java.util.List;

public class SearchTest {

    public static void main(String[] args) {
        SearchService searchService = new SearchService();

        FirebaseContext firebaseContext = new FirebaseContext();

        searchService.loadfirebasedata(firebaseContext);

        List<Book> results = searchService.search("mo", "title");
        List<Book> results1 = searchService.search("9781400033416", "isbn");
        List<Book> results2 = searchService.search("Drama", "genre");
        List<Book> results3 = searchService.search("F. Scott Fitzgerald", "author");

        System.out.println("BOOK TITLE SEARCH");
        System.out.println();
        searchService.printresults(results, 5);
        System.out.println("ISBN SEARCH");
        System.out.println();
        searchService.printresults(results1, 1);
        System.out.println("GENRE SEARCH");
        System.out.println();
        searchService.printresults(results2, 5);
        System.out.println("AUTHOR SEARCH");
        System.out.println();
        searchService.printresults(results3, 6);

    }

}