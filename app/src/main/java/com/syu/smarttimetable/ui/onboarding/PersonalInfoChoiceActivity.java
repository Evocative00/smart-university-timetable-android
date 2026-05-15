package com.syu.smarttimetable.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.main.MainActivity;

public class PersonalInfoChoiceActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private Button btnLoadPreviousInfo;
    private Button btnNewStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_choice);

        userRepository = new UserRepository();

        btnLoadPreviousInfo = findViewById(R.id.btn_load_previous_info);
        btnNewStart = findViewById(R.id.btn_new_start);

        btnLoadPreviousInfo.setOnClickListener(v -> loadPreviousInfo());
        btnNewStart.setOnClickListener(v -> startNewFlow());
    }

    private void loadPreviousInfo() {
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) {
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    
                    if (user != null && user.getDepartment() != null) {
                        // 기존 정보로 이동 (수정 가능하게)
                        Intent intent = new Intent(this, UserInfoActivity.class);
                        intent.putExtra("mode", "edit"); // 편집 모드
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "이전 정보가 없습니다. 새로 입력해주세요.", Toast.LENGTH_SHORT).show();
                        startNewFlow();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "정보 불러오기 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void startNewFlow() {
        // 새로 입력
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("mode", "new"); // 신규 모드
        startActivity(intent);
        finish();
    }
}

