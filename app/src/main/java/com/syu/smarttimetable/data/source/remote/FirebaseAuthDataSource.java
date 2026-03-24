package com.syu.smarttimetable.data.source.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.syu.smarttimetable.data.model.User;

public class FirebaseAuthDataSource {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSource() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}