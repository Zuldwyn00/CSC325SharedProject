package com.csc325.librarymanagementsystem.data;

import com.csc325.librarymanagementsystem.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserContext {
    public static final String USERS_COLLECTION = "user";

    private final Firestore firestore;

    public UserContext(Firestore firestore) {
        this.firestore = firestore;
    }

    // Add a user to firestore - If userId is null/blank, firestore generates one and it is set in the in-memory object so it can still be used downstream
    public boolean recordUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null.");
        }

        DocumentReference userDocument;
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            userDocument = firestore.collection(USERS_COLLECTION).document();
            user.setUserId(userDocument.getId()); // if userId is not directly given, generate it with firestore and set it for the object in memory
        } else {
            userDocument = firestore.collection(USERS_COLLECTION).document(user.getUserId());
        }

        try {
            userDocument.create(user).get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error recording user " + user.getUserId() + ": " + e.getMessage());
            return false;
        }
    }

    public User findUserByUserId(String userId) {
        if (userId == null || userId.isBlank()) {return null;}

        try {
            DocumentSnapshot document = firestore.collection(USERS_COLLECTION).document(userId).get().get();
            if (!document.exists()) {
                return null;
            }

            User user = document.toObject(User.class);
            assert user != null;
            user.setUserId(document.getId()); // firestore document id is the user id but doesnt actually set the userId field, so must set manually
            return user;
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error retrieving user " + userId + ": " + e.getMessage());
        }
        return null;
    }

    //two seperate queries because firestore doesnt support OR queries, we use the first match found but if Id isnt found first we re-loop around the collecton using email
    public User findUserByIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {return null;}

        String[] fields = { "libraryId", "email" };
        for (String field : fields) {
            try {
                ApiFuture<QuerySnapshot> query = firestore.collection(USERS_COLLECTION)
                        .whereEqualTo(field, identifier) // libraryId or email, checks both but technically two different queries. First the id is run, and then the email as two different queries
                        .limit(1) //get first result
                        .get();

                List<QueryDocumentSnapshot> documents = query.get().getDocuments();
                if (documents.isEmpty()) {
                    continue;
                }

                QueryDocumentSnapshot document = documents.get(0);
                User user = document.toObject(User.class);
                user.setUserId(document.getId());
                return user;
            } catch (ExecutionException | InterruptedException e) {
                System.err.println("Error retrieving user by " + field + " '" + identifier + "': " + e.getMessage());
            }
        }
        return null;
    }
}
