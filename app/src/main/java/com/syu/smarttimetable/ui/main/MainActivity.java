package com.syu.smarttimetable.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;

public class MainActivity extends AppCompatActivity {

    private RecommendationRequest recommendationRequest;

    private TextView tvMainTitle;
    private TextView tvMainSubtitle;
    private Button btnStartRecommendation;
    private Button btnViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recommendationRequest = (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");

        bindViews();
        setupUi();
        setupButtons();
    }

    private void bindViews() {
        tvMainTitle = findViewById(R.id.tv_main_title);
        tvMainSubtitle = findViewById(R.id.tv_main_subtitle);
        btnStartRecommendation = findViewById(R.id.btn_start_recommendation);
        btnViewResult = findViewById(R.id.btn_view_result);
    }

    private void setupUi() {
        tvMainTitle.setText(R.string.app_name);

        if (recommendationRequest != null) {
            tvMainSubtitle.setText("하드제약과 소프트제약 입력이 완료되었습니다. 추천 결과를 확인해보세요.");
            btnStartRecommendation.setText("추천 결과 보기");
            btnViewResult.setEnabled(true);
            btnViewResult.setAlpha(1f);
        } else {
            tvMainSubtitle.setText("학점, 고정 과목, 선호 조건을 입력해서 자동 시간표 추천을 시작하세요.");
            btnStartRecommendation.setText("추천 시작하기");
            btnViewResult.setEnabled(false);
            btnViewResult.setAlpha(0.5f);
        }
    }

    private void setupButtons() {
        btnStartRecommendation.setOnClickListener(v -> {
            if (recommendationRequest == null) {
                Intent intent = new Intent(this, HardConstraintActivity.class);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(this, RecommendationActivity.class);
            intent.putExtra("recommendationRequest", recommendationRequest);
            startActivity(intent);
        });

        btnViewResult.setOnClickListener(v -> {
            if (recommendationRequest == null) {
                return;
            }

            Intent intent = new Intent(this, RecommendationActivity.class);
            intent.putExtra("recommendationRequest", recommendationRequest);
            startActivity(intent);
        });
    }
}