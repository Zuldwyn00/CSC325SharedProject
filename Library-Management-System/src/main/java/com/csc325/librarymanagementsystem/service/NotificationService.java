package com.csc325.librarymanagementsystem.service;

import com.csc325.librarymanagementsystem.data.FirebaseContext;
import com.csc325.librarymanagementsystem.model.CheckoutConfirmation;
import com.csc325.librarymanagementsystem.model.Notification;
import com.csc325.librarymanagementsystem.model.User;
import com.csc325.librarymanagementsystem.model.Loan;
import java.text.SimpleDateFormat;
import com.csc325.librarymanagementsystem.model.Book;

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
        String bookListText = buildBookListText(confirmation.getBookIds(), firebaseContext);

        String message = "Checkout complete! Confirmation #: " + confirmationNumber
                + ". Books checked out: " + bookListText
                + ". Please keep this confirmation number as proof of your checkout.";

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
                        "Books Checked Out: " + bookListText + "\n\n" +
                        "Please keep this confirmation number as proof of your checkout.";

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
        User user = Session.getCurrentUser();

        if (user == null || user.getUserId() == null) {
            return;
        }

        FirebaseContext context = firebase == null ? firebaseContext : firebase;

        List<Loan> activeLoans = context.loans().getActiveLoans(user.getUserId());

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        for (Loan loan : activeLoans) {
            if (loan == null || loan.getDueDate() == null || loan.isReturned()) {
                continue;
            }

            long differenceInMilliseconds = loan.getDueDate().getTime() - today.getTime();
            long daysUntilDue = differenceInMilliseconds / (1000 * 60 * 60 * 24);

            if (daysUntilDue >= 0 && daysUntilDue <= daysAhead) {
                String dueDateText = dateFormat.format(loan.getDueDate());
                String bookName = getBookDisplayName(context, loan.getBookId());

                String message = "Due soon: " + bookName
                        + " is due on " + dueDateText + ".";

                Notification notification = new Notification(
                        UUID.randomUUID().toString(),
                        user.getUserId(),
                        message,
                        new Date(),
                        false
                );

                if (!notificationAlreadyExists(user.getUserId(), message, context)) {
                    context.notifications().recordNotification(notification);
                }
            }
        }
    }

    public void sendOverdueAlerts(FirebaseContext firebase) {
        User user = Session.getCurrentUser();

        if (user == null || user.getUserId() == null) {
            return;
        }

        FirebaseContext context = firebase == null ? firebaseContext : firebase;

        List<Loan> activeLoans = context.loans().getActiveLoans(user.getUserId());

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        for (Loan loan : activeLoans) {
            if (loan == null || loan.getDueDate() == null || loan.isReturned()) {
                continue;
            }

            if (loan.getDueDate().before(today)) {
                String dueDateText = dateFormat.format(loan.getDueDate());
                String bookName = getBookDisplayName(context, loan.getBookId());

                String message = "Overdue: " + bookName
                        + " was due on " + dueDateText
                        + ". Please return it as soon as possible.";

                Notification notification = new Notification(
                        UUID.randomUUID().toString(),
                        user.getUserId(),
                        message,
                        new Date(),
                        false
                );

                if (!notificationAlreadyExists(user.getUserId(), message, context)) {
                    context.notifications().recordNotification(notification);
                }
            }
        }
    }
    private String buildBookListText(List<String> bookIds, FirebaseContext context) {
        if (bookIds == null || bookIds.isEmpty()) {
            return "No books listed";
        }

        StringBuilder bookList = new StringBuilder();

        for (int i = 0; i < bookIds.size(); i++) {
            String bookName = getBookDisplayName(context, bookIds.get(i));

            if (i > 0) {
                bookList.append(", ");
            }

            bookList.append(bookName);
        }

        return bookList.toString();
    }

    private String getBookDisplayName(FirebaseContext context, String bookId) {
        Book book = context.getBookById(bookId);

        if (book != null && book.getTitle() != null && !book.getTitle().isBlank()) {
            return book.getTitle();
        }

        return "Book ID " + bookId;
    }

    private boolean notificationAlreadyExists(String userId, String message, FirebaseContext context) {
        List<Notification> notifications = context.notifications().getNotificationsForUser(userId);

        for (Notification notification : notifications) {
            if (notification.getMessage() != null && notification.getMessage().equals(message)) {
                return true;
            }
        }

        return false;
    }
}