package com.syu.smarttimetable.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RepresentativeTimetableRepository {

    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_APP_STATE = "appState";
    private static final String DOCUMENT_REPRESENTATIVE_TIMETABLE = "representativeTimetable";

    private final FirebaseFirestore firestore;

    public RepresentativeTimetableRepository() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getRepresentativeTimetable(String userId) {
        return firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_APP_STATE)
                .document(DOCUMENT_REPRESENTATIVE_TIMETABLE)
                .get();
    }

    public Task<Void> saveRepresentativeTimetable(String userId, String timetableKey, String timetableJson) {
        Map<String, Object> data = new HashMap<>();
        data.put("timetableKey", timetableKey);
        data.put("timetableJson", timetableJson);
        data.put("source", "recommendation");
        data.put("updatedAt", FieldValue.serverTimestamp());

        return firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_APP_STATE)
                .document(DOCUMENT_REPRESENTATIVE_TIMETABLE)
                .set(data);
    }

    public Task<Void> deleteRepresentativeTimetable(String userId) {
        return firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_APP_STATE)
                .document(DOCUMENT_REPRESENTATIVE_TIMETABLE)
                .delete();
    }
}
