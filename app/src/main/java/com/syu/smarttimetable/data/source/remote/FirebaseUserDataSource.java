package com.syu.smarttimetable.data.source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.syu.smarttimetable.data.model.User;

public class FirebaseUserDataSource {

    private final FirebaseFirestore firestore;

    public FirebaseUserDataSource() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> saveUser(User user) {
        return firestore.collection("users")
                .document(user.getUserId())
                .set(user);
    }

    public Task<Void> updateUser(User user) {
        return firestore.collection("users")
                .document(user.getUserId())
                .set(user);
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }
}