package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.AbstractMap;


public class SearchService {

    private List<Book> books = new ArrayList<>();

    public void loadfirebasedata(FirebaseContext fc) {

        books.clear();
        books.addAll(fc.getAllBooks());
    }


    //switch the list to a map so that it can store scores so that i can order with scores
    public List<Book> search(String query, SearchType type) {

        List<Map.Entry<Book, Integer>> scoredResults = new ArrayList<>();

        if (query == null || query.isEmpty() || type == null || books == null) {
            return new ArrayList<>();
        }

        query = query.trim().toLowerCase();

        // huge switch case that filters the search
        for (Book book : books) {

            switch (type) {

                case TITLE: {

                    //split tittle into words for better compares
                    String[] queryWords = query.split("\\s+");
                    String[] titleWords = book.getTitle().toLowerCase().split("\\s+");

                    int totalDistance = 0;
                    int matchCount = 0;

                    for (String qWord : queryWords) {

                        int bestDistance = Integer.MAX_VALUE;

                        //checks every word in the titte and gives them a best distance
                        for (String tWord : titleWords) {

                            int distance = levenshtein(qWord, tWord);

                            //keeps a score of the best distance
                            if (distance < bestDistance) {
                                bestDistance = distance;
                            }
                        }

                        if (bestDistance <= 2) {
                            matchCount++;
                        }

                        totalDistance += bestDistance;
                    }

                    //gives it the best score in the map
                    if (matchCount >= Math.max(1, queryWords.length / 2)) {
                        scoredResults.add(
                                new AbstractMap.SimpleEntry<>(book, totalDistance)
                        );
                    }

                    break;
                }

                case AUTHOR: {

                    for (String author : book.getAuthors()) {

                        int distance = levenshtein(query, author.toLowerCase());

                        if (author.toLowerCase().contains(query)
                                || distance <= 2) {

                            scoredResults.add(
                                    new AbstractMap.SimpleEntry<>(book, distance)
                            );

                            break;
                        }
                    }

                    break;
                }

                case GENRE: {

                    for (String genre : book.getGenres()) {

                        int distance = levenshtein(query, genre.toLowerCase());

                        if (genre.toLowerCase().contains(query)
                                || distance <= 2) {

                            scoredResults.add(
                                    new AbstractMap.SimpleEntry<>(book, distance)
                            );

                            break;
                        }
                    }

                    break;
                }

                case ISBN: {

                    int distance = levenshtein(query, book.getIsbn());

                    if (book.getIsbn().equals(query)
                            || distance <= 2) {

                        scoredResults.add(
                                new AbstractMap.SimpleEntry<>(book, distance)
                        );
                    }

                    break;
                }
            }
        }

        //sorts the scores and then turns it back into a Array list so the rest of the code works fine
        scoredResults.sort(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        List<Book> results = new ArrayList<>();

        for (Map.Entry<Book, Integer> entry : scoredResults) {
            results.add(entry.getKey());
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
