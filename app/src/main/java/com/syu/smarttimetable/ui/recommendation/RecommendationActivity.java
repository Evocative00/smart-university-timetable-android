package com.syu.smarttimetable.ui.recommendation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.timetable.TimetableCacheManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.syu.smarttimetable.data.repository.ImageStorageRepository;
import com.syu.smarttimetable.domain.timetable.TimetableImageExporter;

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

    private MaterialButton btnSaveImage;
    private MaterialButton btnShareImage;
    private View timetableCard;

    private static final int REQUEST_WRITE_STORAGE = 1001;

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
        ImageButton btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

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
        btnSaveImage = findViewById(R.id.btn_save_image);
        btnShareImage = findViewById(R.id.btn_share_image);
        timetableCard = findViewById(R.id.timetable_card);
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

        btnRegenerate.setOnClickListener(v -> {
            TimetableCacheManager.clear();
            loadRecommendations();
        });

        btnSaveImage.setOnClickListener(v -> saveCurrentTimetableImage());
        btnShareImage.setOnClickListener(v -> shareCurrentTimetableImage());
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

        String cacheKey = TimetableCacheManager.generateCacheKey(recommendationRequest);

        List<RecommendationEngine.TimetableScoreTuple> cachedResults =
                TimetableCacheManager.getFromCache(cacheKey);

        if (cachedResults != null) {
            Log.d(TAG, "Using cached recommendation results");

            recommendationResults.clear();
            recommendationResults.addAll(cachedResults);
            currentIndex = 0;

            if (recommendationResults.isEmpty()) {
                showEmptyState("조건에 맞는 시간표를 찾지 못했습니다.\n\n최소학점: "
                        + recommendationRequest.getMinCredits()
                        + "학점\n최대학점: "
                        + recommendationRequest.getMaxCredits()
                        + "학점");
                return;
            }

            contentContainer.setVisibility(View.VISIBLE);
            emptyStateContainer.setVisibility(View.GONE);
            renderCurrentRecommendation();
            return;
        }

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
                RecommendationEngine.RecommendationResult engineResult =
                        engine.recommend(allLectures, recommendationRequest);

                Log.d(TAG, "Recommendation complete.");

                List<DayOfWeek> conflictDays = engineResult != null
                        ? engineResult.getConflictDays()
                        : new ArrayList<>();

                if (conflictDays != null && !conflictDays.isEmpty()) {
                    Log.w(TAG, "Fixed lecture vs preferred free day conflict: " + conflictDays);
                }

                List<RecommendationEngine.TimetableScoreTuple> results = engineResult != null
                        ? engineResult.getRecommendations()
                        : new ArrayList<>();

                TimetableCacheManager.addToCache(cacheKey, results);

                Log.d(TAG, "Results: " + (results == null ? 0 : results.size()));

                runOnUiThread(() -> {
                    try {
                        recommendationResults.clear();

                        if (results != null) {
                            recommendationResults.addAll(results);
                        }

                        currentIndex = 0;

                        if (recommendationResults.isEmpty()) {
                            Log.w(TAG, "No matching timetables found");
                            showEmptyState("조건에 맞는 시간표를 찾지 못했습니다.\n\n최소학점: "
                                    + recommendationRequest.getMinCredits()
                                    + "학점\n최대학점: "
                                    + recommendationRequest.getMaxCredits()
                                    + "학점");
                            return;
                        }

                        contentContainer.setVisibility(View.VISIBLE);
                        emptyStateContainer.setVisibility(View.GONE);
                        renderCurrentRecommendation();

                        if (conflictDays != null && !conflictDays.isEmpty()) {
                            showFreeDayConflictNotice(conflictDays);
                        }
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

    private void showFreeDayConflictNotice(List<DayOfWeek> conflictDays) {
        if (conflictDays == null || conflictDays.isEmpty()) {
            return;
        }

        String formattedDays = formatDayListKorean(conflictDays);
        String message = formattedDays
                + "에는 고정된 수업이 있어서 해당 요일은 공강 선호에서 제외하고 추천했습니다.";

        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle("공강 선호 일부 제외")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show();
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
            tvScreenSubtitle.setText("필수 조건을 만족하는 후보 중 선호 조건 점수가 높은 시간표입니다.");

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

        boolean canRegenerate = recommendationRequest != null;
        btnRegenerate.setEnabled(canRegenerate);

        btnPrev.setAlpha(0.4f);
        btnNext.setAlpha(0.4f);
        btnRegenerate.setAlpha(canRegenerate ? 1f : 0.4f);
    }

    private String dayOfWeekToKorean(DayOfWeek day) {
        if (day == null) return "";

        switch (day) {
            case MONDAY:
                return "월요일";
            case TUESDAY:
                return "화요일";
            case WEDNESDAY:
                return "수요일";
            case THURSDAY:
                return "목요일";
            case FRIDAY:
                return "금요일";
            case SATURDAY:
                return "토요일";
            case SUNDAY:
                return "일요일";
            default:
                return day.name();
        }
    }

    private String formatDayListKorean(List<DayOfWeek> days) {
        if (days == null || days.isEmpty()) return "";

        List<String> names = new ArrayList<>();
        for (DayOfWeek d : days) {
            names.add(dayOfWeekToKorean(d));
        }

        if (names.size() == 1) return names.get(0);

        if (names.size() == 2) {
            // A and B -> "A와 B" (use '와'/'과' choice simplified to '과' when ending with vowel? use '와' for readability)
            return names.get(0) + "과 " + names.get(1);
        }

        // 3 or more: "A, B, C" but for Korean day names we can join with ", " and add " 등"
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            sb.append(names.get(i));
            if (i < names.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    private void saveCurrentTimetableImage() {
        if (timetableCard == null
                || timetableCard.getWidth() == 0
                || timetableCard.getHeight() == 0
                || recommendationResults.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_timetable_not_ready), Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE
            );
            return;
        }

        Bitmap bitmap = TimetableImageExporter.captureView(timetableCard);

        new Thread(() -> {
            try {
                ImageStorageRepository.savePng(this, bitmap);

                runOnUiThread(() ->
                        Toast.makeText(this, getString(R.string.toast_save_success), Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                Log.e(TAG, "Save failed", e);

                runOnUiThread(() ->
                        Toast.makeText(this, getString(R.string.toast_save_failed), Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private void shareCurrentTimetableImage() {
        if (timetableCard == null
                || timetableCard.getWidth() == 0
                || timetableCard.getHeight() == 0
                || recommendationResults.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_timetable_not_ready), Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = TimetableImageExporter.captureView(timetableCard);

        new Thread(() -> {
            try {
                Uri shareUri = ImageStorageRepository.getShareableUri(this, bitmap);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, shareUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                runOnUiThread(() ->
                        startActivity(Intent.createChooser(intent, getString(R.string.share_title)))
                );
            } catch (Exception e) {
                Log.e(TAG, "Share failed", e);

                runOnUiThread(() ->
                        Toast.makeText(this, getString(R.string.toast_share_failed), Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_WRITE_STORAGE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveCurrentTimetableImage();
        } else if (requestCode == REQUEST_WRITE_STORAGE) {
            Toast.makeText(this, getString(R.string.toast_permission_denied), Toast.LENGTH_SHORT).show();
        }
    }
}