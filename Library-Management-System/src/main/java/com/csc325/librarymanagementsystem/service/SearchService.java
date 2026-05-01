package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;

import java.util.ArrayList;
import java.util.List;



public class SearchService {

    private List<Book> books = new ArrayList<>();

    public void loadfirebasedata(FirebaseContext fc) {

        books.clear();
        books.addAll(fc.getAllBooks());
    }


    public List<Book> search(String query, SearchType type) {

        List<Book> results = new ArrayList<>();

        if (query == null || query.isEmpty() || type == null || books == null) {
            return results;
        }

        query = query.trim().toLowerCase();
        //huge switch case that filters the search
        for (Book book : books) {

            switch (type) {

                case TITLE: {
                    String[] queryWords = query.split("\\s+");
                    String[] titleWords = book.getTitle().toLowerCase().split("\\s+");

                    int matchCount = 0;

                    for (String qWord : queryWords) {
                        for (String tWord : titleWords) {
                            if (levenshtein(qWord, tWord) <= 2) {
                                matchCount++;
                                break;
                            }
                        }
                    }

                    if (matchCount >= Math.max(1, queryWords.length / 2)) {
                        results.add(book);
                    }

                    break;
                }

                case AUTHOR: {
                    for (String author : book.getAuthors()) {
                        if (author.toLowerCase().contains(query)
                                || levenshtein(query, author.toLowerCase()) <= 2) {
                            results.add(book);
                            break;
                        }
                    }

                    break;
                }

                case GENRE: {
                    for (String genre : book.getGenres()) {
                        if (genre.toLowerCase().contains(query)) {
                            results.add(book);
                            break;
                        }
                    }

                    break;
                }

                case ISBN: {
                    if (book.getIsbn().equals(query)) {
                        results.add(book);
                    }

                    break;
                }
            }
        }

        return results;
    }

    public int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {

                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[a.length()][b.length()];
    }

    public void printresults(List<Book> results, int max) {

        if (results == null || results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        int limit = Math.min(max, results.size());

        for (int i = 0; i < limit; i++) {
            Book book = results.get(i);

            System.out.println("----- Result " + (i + 1) + " -----");
            System.out.println("Title: " + book.getTitle());
            System.out.println("Authors: " + book.getAuthors());
            System.out.println("Genres: " + book.getGenres());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Quantity: " + book.getQuantity());
            System.out.println();
        }
    }
}
