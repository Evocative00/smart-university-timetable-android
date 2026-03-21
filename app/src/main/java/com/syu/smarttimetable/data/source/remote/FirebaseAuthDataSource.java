package com.syu.smarttimetable.data.source.remote;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthDataSource {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSource() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}