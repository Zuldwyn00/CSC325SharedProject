package com.csc325.librarymanagementsystem.service;

public class EmailService {

    public void sendEmail(String recipientEmail, String subject, String body) {
        // Placeholder email system for now.
        // This simulates sending an email by printing it to the console.

        System.out.println("----- EMAIL SENT -----");
        System.out.println("To: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println(body);
        System.out.println("----------------------");
    }
}