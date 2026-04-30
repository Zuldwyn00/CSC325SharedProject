package com.csc325.librarymanagementsystem.data;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

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

        try (FileInputStream serviceAccount =
                     new FileInputStream("src/main/resources/com.csc325.librarymanagementsystem/firebase/key.json")) {

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