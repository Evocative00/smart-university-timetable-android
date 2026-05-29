package com.syu.smarttimetable.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;
import com.syu.smarttimetable.ui.constraint.SoftConstraintActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecommendationRequest recommendationRequest;
    private int userGrade = 0;
    private String studentId = "";
    private UserRepository userRepository;

    private TextView tvMainTitle;
    private TextView tvMainSubtitle;
    private Button btnStartRecommendation;
    private Button btnViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recommendationRequest = (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");
        userGrade = getIntent().getIntExtra("userGrade", 0);
        studentId = getIntent().getStringExtra("studentId");

        if (studentId == null) {
            studentId = "";
        }

        if (recommendationRequest != null && studentId.isEmpty()) {
            studentId = recommendationRequest.getStudentId();
        }

        if (recommendationRequest != null && userGrade <= 0) {
            userGrade = recommendationRequest.getUserGrade();
        }

        userRepository = new UserRepository();

        bindViews();
        setupUi();
        setupButtons();
        loadUserInfoIfNeeded();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            recommendationRequest = intent.getSerializableExtra(
                    "recommendationRequest",
                    RecommendationRequest.class
            );
        } else {
            recommendationRequest = (RecommendationRequest) intent.getSerializableExtra("recommendationRequest");
        }

        int newGrade = intent.getIntExtra("userGrade", 0);
        if (newGrade > 0) {
            userGrade = newGrade;
        }

        String newStudentId = intent.getStringExtra("studentId");
        if (newStudentId != null) {
            studentId = newStudentId;
        }

        if (recommendationRequest != null && studentId.isEmpty()) {
            studentId = recommendationRequest.getStudentId();
        }

        if (recommendationRequest != null && userGrade <= 0) {
            userGrade = recommendationRequest.getUserGrade();
        }

        setupUi();
    }

    private void bindViews() {
        tvMainTitle = findViewById(R.id.tv_main_title);
        tvMainSubtitle = findViewById(R.id.tv_main_subtitle);
        btnStartRecommendation = findViewById(R.id.btn_start_recommendation);
        btnViewResult = findViewById(R.id.btn_view_result);
    }

    private void setupUi() {
        tvMainTitle.setText("SmartTimetable");

        if (recommendationRequest != null) {
            tvMainSubtitle.setText("필수 조건과 선택 조건 입력이 완료되었습니다. 추천 결과를 확인해보세요.");

            btnStartRecommendation.setText(getString(R.string.btn_view_result));

            btnViewResult.setVisibility(View.VISIBLE);
            btnViewResult.setEnabled(true);
            btnViewResult.setAlpha(1f);
            btnViewResult.setText(getString(R.string.btn_edit_conditions));
        } else {
            tvMainSubtitle.setText("학점, 고정 과목, 선호 조건을 입력해서 자동 시간표 추천을 시작하세요.");

            btnStartRecommendation.setText(getString(R.string.btn_start_recommendation));

            btnViewResult.setVisibility(View.GONE);
            btnViewResult.setEnabled(false);
            btnViewResult.setAlpha(0f);
        }
    }

    private void setupButtons() {
        btnStartRecommendation.setOnClickListener(v -> {
            if (recommendationRequest != null) {
                navigateToRecommendationResult();
                return;
            }

            navigateToHardConstraintWithUserInfo();
        });

        btnViewResult.setOnClickListener(v -> showEditConditionsDialog());
    }

    private void loadUserInfoIfNeeded() {
        if (userGrade > 0 && studentId != null && !studentId.trim().isEmpty()) {
            return;
        }

        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();

        if (firebaseUser == null) {
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot == null || !documentSnapshot.exists()) {
                        return;
                    }

                    Long gradeValue = documentSnapshot.getLong("grade");

                    if (gradeValue != null) {
                        userGrade = gradeValue.intValue();
                    }

                    String studentIdValue = documentSnapshot.getString("studentId");

                    if (studentIdValue != null) {
                        studentId = studentIdValue;
                    }
                });
    }

    private void showEditConditionsDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("입력 조건 수정")
                .setItems(new String[]{"필수 조건 수정", "선택 조건 수정"}, (dialog, which) -> {
                    if (which == 0) {
                        navigateToHardConstraintWithUserInfo();
                    } else {
                        navigateToSoftConstraint();
                    }
                })
                .show();
    }

    private void navigateToSoftConstraint() {
        if (recommendationRequest == null) {
            navigateToHardConstraintWithUserInfo();
            return;
        }

        int targetCredits = (recommendationRequest.getMinCredits()
                + recommendationRequest.getMaxCredits()) / 2;

        HardConstraint hardConstraint = new HardConstraint(
                targetCredits,
                new ArrayList<>(recommendationRequest.getFixedLectureKeys()),
                new ArrayList<>(recommendationRequest.getCompletedCourseCodes())
        );

        int requestUserGrade = userGrade > 0
                ? userGrade
                : recommendationRequest.getUserGrade();

        String requestStudentId = studentId;

        if ((requestStudentId == null || requestStudentId.trim().isEmpty())
                && recommendationRequest.getStudentId() != null) {
            requestStudentId = recommendationRequest.getStudentId();
        }

        Intent intent = new Intent(this, SoftConstraintActivity.class);
        intent.putExtra("hardConstraint", hardConstraint);
        intent.putExtra("userGrade", requestUserGrade);
        intent.putExtra("studentId", requestStudentId);
        startActivity(intent);
    }

    private void navigateToHardConstraintWithUserInfo() {
        if (userGrade > 0 && studentId != null && !studentId.trim().isEmpty()) {
            Intent intent = new Intent(this, HardConstraintActivity.class);
            intent.putExtra("userGrade", userGrade);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
            return;
        }

        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(this, HardConstraintActivity.class);
            intent.putExtra("userGrade", userGrade);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Long gradeValue = documentSnapshot.getLong("grade");

                        if (gradeValue != null) {
                            userGrade = gradeValue.intValue();
                        }

                        String studentIdValue = documentSnapshot.getString("studentId");

                        if (studentIdValue != null) {
                            studentId = studentIdValue;
                        }
                    }

                    Intent intent = new Intent(this, HardConstraintActivity.class);
                    intent.putExtra("userGrade", userGrade);
                    intent.putExtra("studentId", studentId);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Intent intent = new Intent(this, HardConstraintActivity.class);
                    intent.putExtra("userGrade", userGrade);
                    intent.putExtra("studentId", studentId);
                    startActivity(intent);
                });
    }

    private void navigateToRecommendationResult() {
        Intent intent = new Intent(this, RecommendationActivity.class);
        intent.putExtra("recommendationRequest", recommendationRequest);
        startActivity(intent);
    }
}