package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.service.SearchService;
import com.csc325.librarymanagementsystem.model.Book;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SearchServiceTest {

    SearchService searchService = new SearchService();
    FirebaseContext firebaseContext = new FirebaseContext();

    @Test
    void normalsearch() {
        // Test 1: normal search
        //mostly testing the search
        searchService.loadfirebasedata(firebaseContext);
        System.out.println("=== Test 1: Normal Search ===");
        List<Book> results1 = searchService.search("Steve Jobs", SearchType.TITLE);
        searchService.printresults(results1, results1.size());

        assertEquals(4, results1.size());
    }

    @Test
    void max() {

        searchService.loadfirebasedata(firebaseContext);

        System.out.println("=== Test 2: Limit smaller than results ===");

        List<Book> results2 = searchService.search("the", SearchType.TITLE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outputStream));

        searchService.printresults(results2, 2);

        System.setOut(originalOut);

        String printedOutput = outputStream.toString();

        int printedCount = printedOutput.split("Title:").length - 1;

        assertEquals(2, printedCount);
    }

    @Test
    void noresults() {
        // Test 3: no results
        searchService.loadfirebasedata(firebaseContext);
        System.out.println("=== Test 3: No Results ===");
        List<Book> results3 = searchService.search("asdkjasdk", SearchType.TITLE);
        searchService.printresults(results3, 5);

        assertEquals(0, results3.size());
    }

    @Test
    void printResultsRespectsMax() {

        searchService.loadfirebasedata(firebaseContext);

        List<Book> results = searchService.search("Dune", SearchType.TITLE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outputStream));

        searchService.printresults(results, 3);

        System.setOut(originalOut);

        String printedOutput = outputStream.toString();

        int printedCount = printedOutput.split("Title:").length - 1;

        assertTrue(printedCount <= 3);
    }

    @Test
    void emptyresults(){
        // Test 5: empty query
        searchService.loadfirebasedata(firebaseContext);
        System.out.println("=== Test 5: Empty Query ===");
        List<Book> results5 = searchService.search("", SearchType.TITLE);
        searchService.printresults(results5, 5);
        assertEquals(0, results5.size());

    }




}