package com.syu.smarttimetable.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.HardConstraint;
import com.syu.smarttimetable.data.model.SoftConstraint;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

public class MainActivity extends AppCompatActivity {

    private HardConstraint hardConstraint;
    private SoftConstraint softConstraint;
    private RecommendationRequest recommendationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hardConstraint = (HardConstraint) getIntent().getSerializableExtra("hardConstraint");
        softConstraint = (SoftConstraint) getIntent().getSerializableExtra("softConstraint");
        recommendationRequest = (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");

        TextView subtitle = findViewById(R.id.tv_main_subtitle);

        if (recommendationRequest != null) {
            subtitle.setText("하드제약/소프트제약 입력 완료. 자동 추천 로직 연결 준비 상태입니다.");
        } else {
            subtitle.setText("메인 화면은 추후 구현 예정입니다.");
        }
    }
}