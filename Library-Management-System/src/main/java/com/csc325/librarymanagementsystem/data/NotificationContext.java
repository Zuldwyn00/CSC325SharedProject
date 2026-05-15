package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.Notification;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationContext {

    public static final String NOTIFICATIONS_COLLECTION = "notifications";

    private final Firestore db;

    public NotificationContext(Firestore db) {
        this.db = db;
    }

    public boolean recordNotification(Notification notification) {
        if (notification == null || notification.getUserId() == null || notification.getUserId().isBlank()) {
            return false;
        }

        try {
            DocumentReference notificationDocument;

            if (notification.getNotificationId() == null || notification.getNotificationId().isBlank()) {
                notificationDocument = db.collection(NOTIFICATIONS_COLLECTION).document();
                notification.setNotificationId(notificationDocument.getId());
            } else {
                notificationDocument = db.collection(NOTIFICATIONS_COLLECTION).document(notification.getNotificationId());
            }

            notificationDocument.set(notification).get();
            return true;

        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error recording notification: " + e.getMessage());
            return false;
        }
    }

    public List<Notification> getNotificationsForUser(String userId) {
        List<Notification> notifications = new ArrayList<>();

        if (userId == null || userId.isBlank()) {
            return notifications;
        }

        try {
            ApiFuture<QuerySnapshot> query = db.collection(NOTIFICATIONS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .get();

            QuerySnapshot querySnapshot = query.get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                Notification notification = document.toObject(Notification.class);
                notification.setNotificationId(document.getId());
                notifications.add(notification);
            }

        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error getting notifications for user " + userId + ": " + e.getMessage());
        }

        return notifications;
    }

    public boolean markAllRead(String userId) {
        if (userId == null || userId.isBlank()) {
            return false;
        }

        try {
            List<QueryDocumentSnapshot> documents = db.collection(NOTIFICATIONS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("read", false)
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                document.getReference().update("read", true).get();
            }

            return true;

        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error marking notifications as read: " + e.getMessage());
            return false;
        }
    }
}