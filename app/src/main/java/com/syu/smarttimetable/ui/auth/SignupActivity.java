package com.syu.smarttimetable.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.constraint.onboarding.UserInfoActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etName;
    private Button btnSignup;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userRepository = new UserRepository();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.signUp(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();

                    if (firebaseUser != null) {
                        User user = new User(
                                firebaseUser.getUid(),
                                email,
                                name,
                                0,
                                "",
                                "",
                                ""
                        );

                        userRepository.saveUser(user)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(this, UserInfoActivity.class);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "기본 사용자 저장 실패: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                );
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "회원가입 실패: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}