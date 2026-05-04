package com.syu.smarttimetable.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.onboarding.UserInfoActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnMoveSignup = findViewById(R.id.btnMoveSignup);

        btnLogin.setOnClickListener(v -> login());
        btnMoveSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.login(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = userRepository.getCurrentFirebaseUser().getUid();
                    userRepository.getUser(uid)
                            .addOnSuccessListener(doc -> {
                                String name = doc.getString("name");
                                String msg = (name != null && !name.isEmpty())
                                        ? name + "님 환영합니다!"
                                        : "환영합니다!";
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, UserInfoActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "환영합니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, UserInfoActivity.class);
                                startActivity(intent);
                                finish();
                            });
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "로그인 실패: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}