package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;

public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendDueSoonAlerts(FirebaseContext firebase, int daysAhead) {
    }

    public void sendOverdueAlerts(FirebaseContext firebase) {
    }
}
