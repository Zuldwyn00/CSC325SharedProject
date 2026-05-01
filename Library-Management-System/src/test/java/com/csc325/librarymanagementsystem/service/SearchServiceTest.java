package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest {

    public static void main(String[] args) {

        SearchService searchService = new SearchService();
        FirebaseContext firebaseContext = new FirebaseContext();


        // load fake data
        searchService.loadfirebasedata(firebaseContext);

        // Test 1: normal search
        System.out.println("=== Test 1: Normal Search ===");
        List<Book> results1 = searchService.search("Steve Jobs", SearchType.TITLE);
        searchService.printresults(results1, 5);

        // Test 2: more results than max
        System.out.println("=== Test 2: Limit smaller than results ===");
        List<Book> results2 = searchService.search("the", SearchType.TITLE);
        searchService.printresults(results2, 2);

        // Test 3: no results
        System.out.println("=== Test 3: No Results ===");
        List<Book> results3 = searchService.search("asdkjasdk", SearchType.TITLE);
        searchService.printresults(results3, 5);

        // Test 4: max larger than results
        System.out.println("=== Test 4: Max > results ===");
        List<Book> results4 = searchService.search("Dune", SearchType.TITLE);
        searchService.printresults(results4, 10);

        // Test 5: empty query
        System.out.println("=== Test 5: Empty Query ===");
        List<Book> results5 = searchService.search("", SearchType.TITLE);
        searchService.printresults(results5, 5);
    }

}