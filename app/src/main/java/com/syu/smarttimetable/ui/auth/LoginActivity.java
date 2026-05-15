package com.syu.smarttimetable.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.main.MainActivity;
import com.syu.smarttimetable.ui.onboarding.PersonalInfoChoiceActivity;
import com.syu.smarttimetable.ui.onboarding.UserInfoActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnMoveSignup;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnMoveSignup = findViewById(R.id.btnMoveSignup);

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
                    if (userRepository.getCurrentFirebaseUser() == null) {
                        Toast.makeText(this, "로그인 정보를 확인할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String uid = userRepository.getCurrentFirebaseUser().getUid();

                    userRepository.getUser(uid)
                            .addOnSuccessListener(documentSnapshot -> {
                                boolean hasUserInfo = documentSnapshot.exists()
                                        && documentSnapshot.getString("department") != null
                                        && documentSnapshot.getLong("grade") != null
                                        && documentSnapshot.getString("studentId") != null;

                                Intent intent;

                                if (hasUserInfo) {
                                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(this, PersonalInfoChoiceActivity.class);
                                } else {
                                    Toast.makeText(this, "기본정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(this, UserInfoActivity.class);
                                    intent.putExtra("mode", "new");
                                }

                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "기본정보 확인 실패. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(this, UserInfoActivity.class);
                                intent.putExtra("mode", "new");
                                startActivity(intent);
                                finish();
                            });
                })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "로그인 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}