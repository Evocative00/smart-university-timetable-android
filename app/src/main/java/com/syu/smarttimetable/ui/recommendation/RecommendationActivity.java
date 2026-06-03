package com.syu.smarttimetable.ui.recommendation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.RecommendationPreferenceManager;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.LectureTime;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.repository.ImageStorageRepository;
import com.syu.smarttimetable.data.repository.LectureRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationEngine;
import com.syu.smarttimetable.data.model.enums.DayOfWeek;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.domain.timetable.TimetableImageExporter;
import com.syu.smarttimetable.ui.timetable.TimetableCacheManager;

import java.util.ArrayList;
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {

    private static final String TAG = "RecommendationActivity";

    private TextView tvScreenTitle;
    private TextView tvScreenSubtitle;
    private TextView tvRank;
    private TextView tvScore;
    private TextView tvCredits;
    private TextView tvIncludedCount;
    private LinearLayout chipContainer;
    private TimetablePreviewView timetablePreviewView;
    private TextView tvAfterHoursHint;
    private LinearLayout lectureListContainer;
    private LinearLayout emptyStateContainer;
    private TextView tvEmptyGuide;
    private View contentContainer;
    private View bottomActionContainer;
    private View includedLecturesSummaryCard;
    private Button btnPrev;
    private Button btnNext;
    private Button btnRegenerate;

    private RecommendationRequest recommendationRequest;
    private final List<RecommendationEngine.TimetableScoreTuple> recommendationResults = new ArrayList<>();
    private int currentIndex = 0;

    private MaterialButton btnSaveImage;
    private MaterialButton btnShareImage;
    private MaterialButton btnTab1;
    private MaterialButton btnTab2;
    private MaterialButton btnTab3;
    private MaterialButton btnMoreRecommendations;
    private MaterialButton btnConditionEdit;
    private MaterialButton btnFullTimetableView;
    private View btnMoreActions;
    private View timetableCard;

    private RecommendationPreferenceManager preferenceManager;
    private ImageButton btnFavoriteTimetable;

    private static final int REQUEST_WRITE_STORAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recommendationRequest = (RecommendationRequest) getIntent().getSerializableExtra("recommendationRequest");
        preferenceManager = new RecommendationPreferenceManager(this);

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
        tvIncludedCount = findViewById(R.id.tv_included_count);
        chipContainer = findViewById(R.id.chip_container);
        timetablePreviewView = findViewById(R.id.timetable_preview);
        tvAfterHoursHint = findViewById(R.id.tv_after_hours_hint);
        lectureListContainer = findViewById(R.id.lecture_list_container);
        emptyStateContainer = findViewById(R.id.empty_state_container);
        tvEmptyGuide = findViewById(R.id.tv_empty_guide);
        contentContainer = findViewById(R.id.content_container);
        bottomActionContainer = findViewById(R.id.bottom_action_container);
        includedLecturesSummaryCard = findViewById(R.id.included_lectures_summary_card);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        btnRegenerate = findViewById(R.id.btn_regenerate);
        btnSaveImage = findViewById(R.id.btn_save_image);
        btnShareImage = findViewById(R.id.btn_share_image);
        btnTab1 = findViewById(R.id.btn_tab_1);
        btnTab2 = findViewById(R.id.btn_tab_2);
        btnTab3 = findViewById(R.id.btn_tab_3);
        btnMoreRecommendations = findViewById(R.id.btn_more_recommendations);
        btnConditionEdit = findViewById(R.id.btn_condition_edit);
        btnFullTimetableView = findViewById(R.id.btn_full_timetable_view);
        btnMoreActions = findViewById(R.id.btn_more_actions);
        timetableCard = findViewById(R.id.timetable_card);
        btnFavoriteTimetable = findViewById(R.id.btn_favorite_timetable);
    }

    private void setupButtons() {
        if (btnPrev != null) {
            btnPrev.setOnClickListener(v -> moveToRecommendation(currentIndex - 1));
        }

        if (btnNext != null) {
            btnNext.setOnClickListener(v -> moveToRecommendation(currentIndex + 1));
        }

        if (btnRegenerate != null) {
            btnRegenerate.setOnClickListener(v -> regenerateRecommendations());
        }

        if (btnSaveImage != null) {
            btnSaveImage.setOnClickListener(v -> saveCurrentTimetableImage());
        }

        if (btnShareImage != null) {
            btnShareImage.setOnClickListener(v -> shareCurrentTimetableImage());
        }

        if (btnFavoriteTimetable != null) {
            btnFavoriteTimetable.setOnClickListener(v -> toggleTimetableFavorite());
        }

        if (btnTab1 != null) {
            btnTab1.setOnClickListener(v -> moveToRecommendation(0));
        }

        if (btnTab2 != null) {
            btnTab2.setOnClickListener(v -> moveToRecommendation(1));
        }

        if (btnTab3 != null) {
            btnTab3.setOnClickListener(v -> moveToRecommendation(2));
        }

        if (btnMoreRecommendations != null) {
            btnMoreRecommendations.setOnClickListener(v -> showRecommendationPickerSheet());
        }

        if (btnConditionEdit != null) {
            btnConditionEdit.setOnClickListener(v -> finish());
        }

        if (btnMoreActions != null) {
            btnMoreActions.setOnClickListener(this::showMoreActionsMenu);
        }

        if (includedLecturesSummaryCard != null) {
            includedLecturesSummaryCard.setOnClickListener(v -> showIncludedLecturesSheet());
        }

        if (btnFullTimetableView != null) {
            btnFullTimetableView.setOnClickListener(v -> showFullTimetableSheet());
        }
    }

    private void moveToRecommendation(int targetIndex) {
        if (recommendationResults.isEmpty()) {
            return;
        }

        if (targetIndex < 0 || targetIndex >= recommendationResults.size()) {
            return;
        }

        currentIndex = targetIndex;
        renderCurrentRecommendation();
    }

    private void regenerateRecommendations() {
        TimetableCacheManager.clear();
        loadRecommendations();
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

            setContentVisible(true);
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

                        setContentVisible(true);
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
        setContentVisible(false);
        emptyStateContainer.setVisibility(View.VISIBLE);

        TextView emptyMessage = findViewById(R.id.tv_empty_message);
        emptyMessage.setText("추천 시간표를 생성하는 중입니다...");

        if (tvEmptyGuide != null) {
            tvEmptyGuide.setVisibility(View.GONE);
        }

        setNavigationEnabled(false);
        setButtonEnabled(btnRegenerate, false);
    }

    private void renderCurrentRecommendation() {
        if (recommendationResults.isEmpty()) {
            showEmptyState("추천 결과가 없습니다.");
            return;
        }

        try {
            Log.d(TAG, "Rendering recommendation " + (currentIndex + 1));

            RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);

            tvScreenTitle.setText(getString(R.string.recommendation_title));
            tvScreenSubtitle.setText(getString(R.string.recommendation_subtitle_redesign));

            tvRank.setText(getString(R.string.recommendation_plan_label, currentIndex + 1));
            tvScore.setText(getRecommendationLevelLabel(current.getScore()));
            tvCredits.setText(current.getTimetable().getTotalCredits() + "학점");

            RecommendationAdapter.renderPreferenceChips(
                    this,
                    chipContainer,
                    recommendationRequest
            );

            Log.d(TAG, "Rendering compact timetable preview...");
            if (timetablePreviewView != null) {
                timetablePreviewView.setTimetable(
                        current.getTimetable(),
                        9,
                        18,
                        this::showLectureDetailSheet
                );
            }

            updateAfterHoursHint(current);
            updateIncludedLecturesSummary(current);
            updateRecommendationTabs();
            updateTimetableFavoriteButton();

            setNavigationEnabled(true);
            setButtonEnabled(btnRegenerate, true);

            Log.d(TAG, "Rendering complete");
        } catch (Exception e) {
            Log.e(TAG, "Error rendering recommendation", e);
            showEmptyState("시간표를 표시하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showEmptyState(String message) {
        setContentVisible(false);
        emptyStateContainer.setVisibility(View.VISIBLE);

        TextView emptyMessage = findViewById(R.id.tv_empty_message);
        emptyMessage.setText(message);

        if (tvEmptyGuide != null) {
            tvEmptyGuide.setVisibility(View.VISIBLE);
        }

        setNavigationEnabled(false);

        boolean canRegenerate = recommendationRequest != null;
        setButtonEnabled(btnRegenerate, canRegenerate);
    }

    private void setContentVisible(boolean visible) {
        if (contentContainer != null) {
            contentContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisibility(visible ? View.GONE : View.VISIBLE);
        }
        if (bottomActionContainer != null) {
            bottomActionContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void setNavigationEnabled(boolean enabled) {
        setButtonEnabled(btnPrev, enabled && currentIndex > 0);
        setButtonEnabled(btnNext, enabled && currentIndex < recommendationResults.size() - 1);
        setButtonEnabled(btnTab1, enabled && recommendationResults.size() >= 1);
        setButtonEnabled(btnTab2, enabled && recommendationResults.size() >= 2);
        setButtonEnabled(btnTab3, enabled && recommendationResults.size() >= 3);
        setButtonEnabled(btnMoreRecommendations, enabled && recommendationResults.size() > 3);
    }

    private void setButtonEnabled(View button, boolean enabled) {
        if (button == null) {
            return;
        }
        button.setEnabled(enabled);
        button.setAlpha(enabled ? 1f : 0.42f);
    }

    private void updateRecommendationTabs() {
        updateRecommendationTab(btnTab1, 0);
        updateRecommendationTab(btnTab2, 1);
        updateRecommendationTab(btnTab3, 2);

        if (btnMoreRecommendations == null) {
            return;
        }

        boolean hasMore = recommendationResults.size() > 3;
        btnMoreRecommendations.setVisibility(hasMore ? View.VISIBLE : View.GONE);
        if (!hasMore) {
            return;
        }

        boolean selectedFromMore = currentIndex >= 3;
        btnMoreRecommendations.setText(selectedFromMore
                ? getString(R.string.recommendation_plan_short, currentIndex + 1)
                : getString(R.string.recommendation_more));
        btnMoreRecommendations.setChecked(selectedFromMore);
    }

    private void updateRecommendationTab(MaterialButton button, int index) {
        if (button == null) {
            return;
        }

        boolean exists = recommendationResults.size() > index;
        button.setVisibility(exists ? View.VISIBLE : View.GONE);
        if (!exists) {
            return;
        }

        button.setText(getString(R.string.recommendation_plan_short, index + 1));
        button.setChecked(currentIndex == index);
    }

    private void updateIncludedLecturesSummary(RecommendationEngine.TimetableScoreTuple current) {
        if (tvIncludedCount == null || current == null || current.getTimetable() == null) {
            return;
        }

        int count = current.getTimetable().getLecturesReadOnly().size();
        tvIncludedCount.setText(getString(R.string.included_lectures_summary_format, count));

        if (lectureListContainer != null) {
            lectureListContainer.removeAllViews();
        }
    }

    private void updateAfterHoursHint(RecommendationEngine.TimetableScoreTuple current) {
        if (tvAfterHoursHint == null || current == null || current.getTimetable() == null) {
            return;
        }

        int afterHoursCount = countLecturesAfterHour(current.getTimetable(), 18);
        if (afterHoursCount > 0) {
            tvAfterHoursHint.setText(getString(R.string.recommendation_after_hours_format, afterHoursCount));
            tvAfterHoursHint.setVisibility(View.VISIBLE);
        } else {
            tvAfterHoursHint.setVisibility(View.GONE);
        }
    }

    private int countLecturesAfterHour(Timetable timetable, int hour) {
        if (timetable == null || timetable.getLecturesReadOnly() == null) {
            return 0;
        }

        int threshold = hour * 60;
        int count = 0;
        for (Lecture lecture : timetable.getLecturesReadOnly()) {
            if (lecture == null || lecture.getTimes() == null) {
                continue;
            }

            boolean counted = false;
            for (LectureTime time : lecture.getTimes()) {
                if (time != null && time.getEndTime() > threshold) {
                    count++;
                    counted = true;
                    break;
                }
            }
            if (counted) {
                continue;
            }
        }
        return count;
    }

    private String getRecommendationLevelLabel(int score) {
        if (recommendationResults.isEmpty()) {
            return getString(R.string.recommendation_rating_high);
        }

        int total = recommendationResults.size();
        int highRankLimit = Math.max(1, (int) Math.ceil(total * 0.3));
        int normalRankLimit = Math.max(highRankLimit + 1, (int) Math.ceil(total * 0.7));

        int topScore = recommendationResults.get(0).getScore();
        double ratioToBest = topScore > 0 ? score / (double) topScore : 0.0;

        if (currentIndex < highRankLimit || ratioToBest >= 0.85) {
            return getString(R.string.recommendation_rating_high);
        }

        if (currentIndex < normalRankLimit || ratioToBest >= 0.65) {
            return getString(R.string.recommendation_rating_normal);
        }

        return getString(R.string.recommendation_rating_qualified);
    }

    private void showRecommendationPickerSheet() {
        if (recommendationResults.isEmpty()) {
            return;
        }

        if (btnMoreRecommendations != null) {
            btnMoreRecommendations.setChecked(currentIndex >= 3);
        }

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setOnDismissListener(sheet -> updateRecommendationTabs());
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_recommendation_picker, null);
        LinearLayout container = view.findViewById(R.id.recommendation_picker_container);

        for (int i = 0; i < recommendationResults.size(); i++) {
            RecommendationEngine.TimetableScoreTuple result = recommendationResults.get(i);
            TextView item = new TextView(this);
            String text = getString(
                    R.string.recommendation_picker_item_format,
                    i + 1,
                    result.getTimetable().getTotalCredits(),
                    getRecommendationLevelLabelForIndex(i)
            );
            item.setText(text);
            item.setTextSize(14);
            item.setTextColor(ContextCompat.getColor(this, R.color.smart_text_primary));
            item.setPadding(dp(16), dp(14), dp(16), dp(14));
            item.setBackgroundResource(i == currentIndex
                    ? R.drawable.bg_recommendation_picker_item_selected
                    : R.drawable.bg_recommendation_picker_item);
            item.setClickable(true);
            item.setFocusable(true);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, dp(10));
            item.setLayoutParams(params);

            final int targetIndex = i;
            item.setOnClickListener(v -> {
                dialog.dismiss();
                moveToRecommendation(targetIndex);
            });

            container.addView(item);
        }

        dialog.setContentView(view);
        dialog.show();
    }

    private String getRecommendationLevelLabelForIndex(int index) {
        if (index < 0 || index >= recommendationResults.size()) {
            return getString(R.string.recommendation_rating_qualified);
        }

        int previousIndex = currentIndex;
        currentIndex = index;
        String label = getRecommendationLevelLabel(recommendationResults.get(index).getScore());
        currentIndex = previousIndex;
        return label;
    }

    private void showIncludedLecturesSheet() {
        if (recommendationResults.isEmpty()) {
            return;
        }

        RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_included_lectures, null);
        LinearLayout container = view.findViewById(R.id.included_lecture_list_container);

        RecommendationAdapter.renderLectureList(this, container, current.getTimetable(), this::showLectureDetailSheet);

        dialog.setContentView(view);
        dialog.show();
    }


    private void showFullTimetableSheet() {
        if (recommendationResults.isEmpty()) {
            return;
        }

        RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_full_timetable, null);

        TextView tvTitle = view.findViewById(R.id.tv_full_timetable_title);
        TimetablePreviewView fullPreview = view.findViewById(R.id.full_timetable_preview);

        if (tvTitle != null) {
            tvTitle.setText(getString(R.string.recommendation_full_timetable_title, currentIndex + 1));
        }

        if (fullPreview != null) {
            fullPreview.setTimetable(current.getTimetable(), 9, 21, this::showLectureDetailSheet);
        }

        dialog.setContentView(view);
        dialog.show();
    }


    private void showLectureDetailSheet(Lecture lecture) {
        if (lecture == null) {
            return;
        }

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_lecture_detail, null);

        TextView tvCourseName = view.findViewById(R.id.tv_detail_course_name);
        TextView tvProfessor = view.findViewById(R.id.tv_detail_professor);
        TextView tvMeta = view.findViewById(R.id.tv_detail_meta);
        TextView tvTime = view.findViewById(R.id.tv_detail_time);

        tvCourseName.setText(lecture.getCourseName());
        tvProfessor.setText(lecture.getProfessor());
        tvMeta.setText(RecommendationAdapter.buildLectureMetaText(lecture));
        tvTime.setText(RecommendationAdapter.buildLectureTimeText(lecture));

        dialog.setContentView(view);
        dialog.show();
    }

    private void showMoreActionsMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(getString(R.string.menu_share_timetable));
        popupMenu.getMenu().add(getString(R.string.menu_regenerate_timetable));
        popupMenu.setOnMenuItemClickListener(item -> {
            String title = String.valueOf(item.getTitle());
            if (title.equals(getString(R.string.menu_share_timetable))) {
                shareCurrentTimetableImage();
                return true;
            }

            if (title.equals(getString(R.string.menu_regenerate_timetable))) {
                regenerateRecommendations();
                return true;
            }

            return false;
        });
        popupMenu.show();
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
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
            return names.get(0) + "과 " + names.get(1);
        }

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

    private String getTimetableUniqueKey(com.syu.smarttimetable.data.model.Timetable timetable) {
        if (timetable == null || timetable.getLecturesReadOnly().isEmpty()) {
            return "empty_timetable";
        }

        List<Lecture> lectures = new ArrayList<>(timetable.getLecturesReadOnly());
        lectures.sort((first, second) -> safeKeyPart(first == null ? null : first.getCourseCode())
                .compareTo(safeKeyPart(second == null ? null : second.getCourseCode())));

        StringBuilder builder = new StringBuilder("timetable_");
        for (Lecture lecture : lectures) {
            if (lecture == null) {
                continue;
            }
            builder.append(safeKeyPart(lecture.getCourseCode()))
                    .append("_")
                    .append(safeKeyPart(lecture.getCourseName()))
                    .append("_")
                    .append(safeKeyPart(lecture.getProfessor()))
                    .append("|");
        }
        return builder.toString();
    }

    private String safeKeyPart(String value) {
        return value == null ? "" : value.trim();
    }

    private void toggleTimetableFavorite() {
        if (recommendationResults.isEmpty() || preferenceManager == null) {
            return;
        }

        RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);
        String timetableKey = getTimetableUniqueKey(current.getTimetable());
        boolean isFavorite = preferenceManager.isTimetableFavorite(timetableKey);

        if (isFavorite) {
            preferenceManager.removeTimetableFavorite(timetableKey);
            Toast.makeText(this, "대표 시간표 설정을 해제했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String timetableJson = new Gson().toJson(current.getTimetable());
                preferenceManager.setTimetableFavorite(timetableKey, timetableJson);
                Toast.makeText(this, "대표 시간표로 설정했습니다.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Error saving favorite timetable", e);
                Toast.makeText(this, "대표 시간표 설정에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        updateTimetableFavoriteButton();
    }

    private void updateTimetableFavoriteButton() {
        if (btnFavoriteTimetable == null || recommendationResults.isEmpty() || preferenceManager == null) {
            return;
        }

        RecommendationEngine.TimetableScoreTuple current = recommendationResults.get(currentIndex);
        String timetableKey = getTimetableUniqueKey(current.getTimetable());
        boolean isFavorite = preferenceManager.isTimetableFavorite(timetableKey);

        btnFavoriteTimetable.setImageDrawable(ContextCompat.getDrawable(
                this,
                isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_border
        ));
        btnFavoriteTimetable.setColorFilter(ContextCompat.getColor(
                this,
                isFavorite ? R.color.smart_warning_yellow : R.color.smart_text_secondary
        ));
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
