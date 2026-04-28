package com.csc325.librarymanagementsystem;

import com.csc325.librarymanagementsystem.model.Book;
import com.csc325.librarymanagementsystem.service.SearchService;

import java.util.ArrayList;
import java.util.List;

public class SearchTest {

    public static void main(String[] args) {
        SearchService searchService = new SearchService();

        searchService.loadTestData();

        List<Book> results = searchService.search("Mo", "title");

        searchService.printresults(results, 5);
        }

}