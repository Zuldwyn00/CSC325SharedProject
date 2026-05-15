package com.csc325.librarymanagementsystem.model;

import java.util.Date;

public class Notification {
    private String notificationId;
    private String userId;
    private String message;
    private Date createdAt;
    private boolean read;

    public Notification() {
    }

    public Notification(String notificationId, String userId, String message, Date createdAt, boolean read) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.read = read;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}