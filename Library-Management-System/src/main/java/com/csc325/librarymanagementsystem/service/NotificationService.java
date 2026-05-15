package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Notification;
import com.csc325.librarymanagementsystem.model.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationService {
    private final EmailService emailService;
    private final FirebaseContext firebaseContext;

    public NotificationService(EmailService emailService) {
        this(emailService, new FirebaseContext());
    }

    public NotificationService(EmailService emailService, FirebaseContext firebaseContext) {
        this.emailService = emailService;
        this.firebaseContext = firebaseContext;
    }

    public void sendCheckoutConfirmation(User user, CheckoutConfirmation confirmation) {
        if (user == null || confirmation == null) {
            return;
        }

        String confirmationNumber = confirmation.getConfirmationNumber();

        String message = "Checkout complete! Confirmation #: " + confirmationNumber;

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                user.getUserId(),
                message,
                new Date(),
                false
        );

        firebaseContext.notifications().recordNotification(notification);

        String emailSubject = "Library Checkout Confirmation";

        String emailBody =
                "Your checkout is complete.\n\n" +
                        "Confirmation Number: " + confirmationNumber + "\n\n" +
                        "Please bring this confirmation number to the front desk to pick up your books.";

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
        }
    }

    public List<Notification> getNotificationsForUser(User user) {
        if (user == null || user.getUserId() == null) {
            return List.of();
        }

        return firebaseContext.notifications().getNotificationsForUser(user.getUserId());
    }

    public void markAllRead(User user) {
        if (user == null || user.getUserId() == null) {
            return;
        }

        firebaseContext.notifications().markAllRead(user.getUserId());
    }

    public void sendDueSoonAlerts(FirebaseContext firebase, int daysAhead) {
    }

    public void sendOverdueAlerts(FirebaseContext firebase) {
    }
}