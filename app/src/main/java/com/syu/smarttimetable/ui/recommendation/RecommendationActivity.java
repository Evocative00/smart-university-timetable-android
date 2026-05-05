package com.syu.smarttimetable.ui.recommendation;

import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "RecommendationActivity";

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
            Log.e(TAG, "recommendationRequest is null");
            showEmptyState("추천 요청 정보가 없습니다.");
            return;
        }

        Log.d(TAG, "Recommendation Request Details:");
        Log.d(TAG, "  Min Credits: " + recommendationRequest.getMinCredits());
        Log.d(TAG, "  Max Credits: " + recommendationRequest.getMaxCredits());
        Log.d(TAG, "  Fixed Lectures: " + recommendationRequest.getFixedLectureKeys().size());
        Log.d(TAG, "  Completed Courses: " + recommendationRequest.getCompletedCourseCodes().size());

        showLoadingState();

        new Thread(() -> {
            try {
                Log.d(TAG, "Starting recommendation calculation...");

                LectureRepository lectureRepository = new LectureRepository();
                List<Lecture> allLectures = lectureRepository.getAllLectures();

                Log.d(TAG, "Loaded " + (allLectures == null ? 0 : allLectures.size()) + " lectures");

                if (allLectures == null || allLectures.isEmpty()) {
                    Log.e(TAG, "No lectures found");
                    runOnUiThread(() -> showEmptyState("강의 데이터가 없습니다."));
                    return;
                }

                RecommendationEngine engine = new RecommendationEngine();
                List<RecommendationEngine.TimetableScoreTuple> results =
                        engine.recommend(allLectures, recommendationRequest);

                Log.d(TAG, "Recommendation complete. Results: " + (results == null ? 0 : results.size()));

                runOnUiThread(() -> {
                    try {
                        recommendationResults.clear();
                        recommendationResults.addAll(results);
                        currentIndex = 0;

                        if (recommendationResults.isEmpty()) {
                            Log.w(TAG, "No matching timetables found");
                            showEmptyState("조건에 맞는 시간표를 찾지 못했습니다.\n\n최소학점: " +
                                recommendationRequest.getMinCredits() + "학점\n최대학점: " +
                                recommendationRequest.getMaxCredits() + "학점");
                            return;
                        }

                        contentContainer.setVisibility(View.VISIBLE);
                        emptyStateContainer.setVisibility(View.GONE);
                        renderCurrentRecommendation();
                    } catch (Exception e) {
                        Log.e(TAG, "Error in UI update", e);
                        showEmptyState("시간표를 표시하는 중 오류가 발생했습니다.");
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error during recommendation", e);
                runOnUiThread(() -> showEmptyState("추천 결과를 불러오는 중 오류가 발생했습니다."));
            }
        }).start();
    }

    private void showLoadingState() {
        contentContainer.setVisibility(View.GONE);
        emptyStateContainer.setVisibility(View.VISIBLE);

        TextView emptyMessage = findViewById(R.id.tv_empty_message);
        emptyMessage.setText("추천 시간표를 생성하는 중입니다...");

        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        btnRegenerate.setEnabled(false);
    }

    private void renderCurrentRecommendation() {
        if (recommendationResults.isEmpty()) {
            showEmptyState("추천 결과가 없습니다.");
            return;
        }

        try {
            Log.d(TAG, "Rendering recommendation " + (currentIndex + 1));

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

            Log.d(TAG, "Rendering timetable grid...");
            RecommendationAdapter.renderTimetableGrid(
                    this,
                    timetableTable,
                    current.getTimetable()
            );

            Log.d(TAG, "Rendering lecture list...");
            RecommendationAdapter.renderLectureList(
                    this,
                    lectureListContainer,
                    current.getTimetable()
            );

            btnPrev.setEnabled(currentIndex > 0);
            btnNext.setEnabled(currentIndex < recommendationResults.size() - 1);
            btnRegenerate.setEnabled(true);

            btnPrev.setAlpha(currentIndex > 0 ? 1f : 0.4f);
            btnNext.setAlpha(currentIndex < recommendationResults.size() - 1 ? 1f : 0.4f);
            btnRegenerate.setAlpha(1f);

            Log.d(TAG, "Rendering complete");
        } catch (Exception e) {
            Log.e(TAG, "Error rendering recommendation", e);
            showEmptyState("시간표를 표시하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showEmptyState(String message) {
        contentContainer.setVisibility(View.GONE);
        emptyStateContainer.setVisibility(View.VISIBLE);

        TextView emptyMessage = findViewById(R.id.tv_empty_message);
        emptyMessage.setText(message);

        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        btnRegenerate.setEnabled(true);

        btnPrev.setAlpha(0.4f);
        btnNext.setAlpha(0.4f);
        btnRegenerate.setAlpha(1f);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}