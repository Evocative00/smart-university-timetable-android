package com.syu.smarttimetable.ui.recommendation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.util.ArrayList;
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {

    private TextView tvScreenTitle;
    private TextView tvScreenSubtitle;
    private TextView tvRank;
    private TextView tvScore;
    private TextView tvCredits;
    private LinearLayout chipContainer;
    private TableLayout timetableTable;
    private LinearLayout lectureListContainer;
    private LinearLayout emptyStateContainer;
    private View contentContainer;
    private Button btnPrev;
    private Button btnNext;
    private Button btnRegenerate;

    private RecommendationRequest recommendationRequest;
    private final List<RecommendationEngine.TimetableScoreTuple> recommendationResults = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recommendationRequest = (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");

        bindViews();
        setupButtons();
        loadRecommendations();
    }

    private void bindViews() {
        tvScreenTitle = findViewById(R.id.tv_screen_title);
        tvScreenSubtitle = findViewById(R.id.tv_screen_subtitle);
        tvRank = findViewById(R.id.tv_rank);
        tvScore = findViewById(R.id.tv_score);
        tvCredits = findViewById(R.id.tv_credits);
        chipContainer = findViewById(R.id.chip_container);
        timetableTable = findViewById(R.id.timetable_table);
        lectureListContainer = findViewById(R.id.lecture_list_container);
        emptyStateContainer = findViewById(R.id.empty_state_container);
        contentContainer = findViewById(R.id.content_container);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        btnRegenerate = findViewById(R.id.btn_regenerate);
    }

    private void setupButtons() {
        btnPrev.setOnClickListener(v -> {
            if (recommendationResults.isEmpty()) {
                return;
            }

            if (currentIndex > 0) {
                currentIndex--;
                renderCurrentRecommendation();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (recommendationResults.isEmpty()) {
                return;
            }

            if (currentIndex < recommendationResults.size() - 1) {
                currentIndex++;
                renderCurrentRecommendation();
            }
        });

        btnRegenerate.setOnClickListener(v -> loadRecommendations());
    }

    private void loadRecommendations() {
        if (recommendationRequest == null) {
            showEmptyState("추천 요청 정보가 없습니다.");
            return;
        }

        LectureRepository lectureRepository = new LectureRepository();
        List<Lecture> allLectures = lectureRepository.getAllLectures();

        if (allLectures == null || allLectures.isEmpty()) {
            showEmptyState("강의 데이터가 없습니다.");
            return;
        }

        RecommendationEngine engine = new RecommendationEngine();
        List<RecommendationEngine.TimetableScoreTuple> results =
                engine.recommend(allLectures, recommendationRequest);

        recommendationResults.clear();
        recommendationResults.addAll(results);
        currentIndex = 0;

        if (recommendationResults.isEmpty()) {
            showEmptyState("조건에 맞는 시간표를 찾지 못했습니다.");
            return;
        }

        contentContainer.setVisibility(View.VISIBLE);
        emptyStateContainer.setVisibility(View.GONE);
        renderCurrentRecommendation();
    }

    private void renderCurrentRecommendation() {
        if (recommendationResults.isEmpty()) {
            showEmptyState("추천 결과가 없습니다.");
            return;
        }

        RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);

        tvScreenTitle.setText("추천 시간표");
        tvScreenSubtitle.setText("하드제약을 만족하는 후보 중 소프트제약 점수가 높은 시간표입니다.");

        tvRank.setText((currentIndex + 1) + " / " + recommendationResults.size());
        tvScore.setText(String.valueOf(current.getScore()));
        tvCredits.setText(current.getTimetable().getTotalCredits() + "학점");

        RecommendationAdapter.renderPreferenceChips(
                this,
                chipContainer,
                recommendationRequest
        );

        RecommendationAdapter.renderTimetableGrid(
                this,
                timetableTable,
                current.getTimetable()
        );

        RecommendationAdapter.renderLectureList(
                this,
                lectureListContainer,
                current.getTimetable()
        );

        btnPrev.setEnabled(currentIndex > 0);
        btnNext.setEnabled(currentIndex < recommendationResults.size() - 1);

        btnPrev.setAlpha(currentIndex > 0 ? 1f : 0.4f);
        btnNext.setAlpha(currentIndex < recommendationResults.size() - 1 ? 1f : 0.4f);
    }

    private void showEmptyState(String message) {
        contentContainer.setVisibility(View.GONE);
        emptyStateContainer.setVisibility(View.VISIBLE);

        TextView emptyMessage = findViewById(R.id.tv_empty_message);
        emptyMessage.setText(message);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}