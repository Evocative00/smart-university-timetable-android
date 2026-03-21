package com.syu.smarttimetable.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.source.remote.FirebaseAuthDataSource;
import com.syu.smarttimetable.data.source.remote.FirebaseUserDataSource;

public class UserRepository {

    private final FirebaseAuthDataSource authDataSource;
    private final FirebaseUserDataSource userDataSource;

    public UserRepository() {
        this.authDataSource = new FirebaseAuthDataSource();
        this.userDataSource = new FirebaseUserDataSource();
    }

    public Task<AuthResult> signUp(String email, String password) {
        return authDataSource.getFirebaseAuth()
                .createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return authDataSource.getFirebaseAuth()
                .signInWithEmailAndPassword(email, password);
    }

    public void logout() {
        authDataSource.getFirebaseAuth().signOut();
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return authDataSource.getFirebaseAuth().getCurrentUser();
    }

    public void saveUser(User user) {
        userDataSource.saveUser(user);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        return userDataSource.getFirestore()
                .collection("users")
                .document(userId)
                .get();
    }
}