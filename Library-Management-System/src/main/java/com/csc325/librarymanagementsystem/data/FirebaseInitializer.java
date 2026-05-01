package com.csc325.librarymanagementsystem.data;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitializer {

    private static Firestore db;

    private FirebaseInitializer() {
    }

    public static Firestore getFirestore() {
        // Only initialize if there isn't already an active firestore instance
        if (db == null) {
            initialize();
        }
        return db;
    }

    private static void initialize() {
        if (!FirebaseApp.getApps().isEmpty()) {
            db = FirestoreClient.getFirestore();
            return;
        }

        try (InputStream serviceAccount = FirebaseInitializer.class.getResourceAsStream(
                "/com/csc325/librarymanagementsystem/firebase/key.json")) {

            if (serviceAccount == null) {
                throw new IOException("Firebase key resource not found");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}