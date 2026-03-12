package com.syu.smarttimetable.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthManager {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}