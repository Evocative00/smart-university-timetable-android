package com.syu.smarttimetable.ui.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.RecommendationPreferenceManager;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;
import com.syu.smarttimetable.ui.main.MainNavigationActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;
import com.google.gson.Gson;

public class TimetableFragment extends Fragment {

    private static final String TAG = "TimetableFragment";

    private RecommendationRequest recommendationRequest;
    private int userGrade = 0;
    private String studentId = "";
    private boolean isUserInfoLoaded = false;
    private UserRepository userRepository;
    private RecommendationPreferenceManager preferenceManager;

    private MaterialButton btnCreate;
    private MaterialButton btnViewRecommendation;
    private TextView tvUserInfoStatus;
    private TextView tvFavoriteEmpty;
    private ImageButton btnFavoriteStar;
    private View favoriteDivider;
    private android.widget.HorizontalScrollView favoriteTimetableScroll;
    private TableLayout favoriteTimetableTable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository();
        refreshRecommendationRequest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Context가 보장되는 시점에 preferenceManager 초기화
        preferenceManager = new RecommendationPreferenceManager(requireContext());

        btnCreate = view.findViewById(R.id.btn_create_timetable);
        btnViewRecommendation = view.findViewById(R.id.btn_view_recommendation);
        tvUserInfoStatus = view.findViewById(R.id.tv_user_info_status);
        tvFavoriteEmpty = view.findViewById(R.id.tv_favorite_empty);
        btnFavoriteStar = view.findViewById(R.id.btn_favorite_star);
        favoriteDivider = view.findViewById(R.id.favorite_divider);
        favoriteTimetableScroll = view.findViewById(R.id.favorite_timetable_scroll);
        favoriteTimetableTable = view.findViewById(R.id.favorite_timetable_table);

        // 사용자 정보 로딩 전까지 버튼 비활성화
        btnCreate.setEnabled(false);
        btnCreate.setAlpha(0.5f);

        btnCreate.setOnClickListener(v -> navigateToCreateTimetable());
        btnViewRecommendation.setOnClickListener(v -> navigateToRecommendation());

        updateRecommendationButtonVisibility();
        displayFavoriteTimetables();
        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecommendationRequest();
        updateRecommendationButtonVisibility();
        displayFavoriteTimetables();
        loadUserInfo();
    }

    private void refreshRecommendationRequest() {
        if (getActivity() instanceof MainNavigationActivity) {
            RecommendationRequest req =
                    ((MainNavigationActivity) getActivity()).getLastRecommendationRequest();
            if (req != null) {
                recommendationRequest = req;
            }
        }
        Log.d(TAG, "refreshRecommendationRequest: " + (recommendationRequest != null ? "있음" : "없음"));
    }

    private void updateRecommendationButtonVisibility() {
        if (btnViewRecommendation == null) return;
        btnViewRecommendation.setVisibility(
                recommendationRequest != null ? View.VISIBLE : View.GONE
        );
        Log.d(TAG, "updateRecommendationButtonVisibility: " + (recommendationRequest != null ? "VISIBLE" : "GONE"));
    }

    private void loadUserInfo() {
        setUserInfoStatus("사용자 정보를 불러오는 중...", true);

        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) {
            onUserInfoLoadFailed("로그인 정보를 확인할 수 없습니다.");
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(doc -> {
                    if (!isAdded()) return;
                    if (doc == null || !doc.exists()) {
                        onUserInfoLoadFailed("사용자 정보를 찾을 수 없습니다.");
                        return;
                    }

                    Long grade = doc.getLong("grade");
                    String sid = doc.getString("studentId");

                    if (grade != null && grade > 0) userGrade = grade.intValue();
                    if (sid != null && !sid.isEmpty()) studentId = sid;

                    if (userGrade == 0 || studentId.isEmpty()) {
                        onUserInfoLoadFailed("학년/학번 정보가 없습니다.\n프로필에서 설정해주세요.");
                        return;
                    }

                    isUserInfoLoaded = true;
                    onUserInfoLoadSuccess();
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    Log.e(TAG, "Firestore load failed", e);
                    onUserInfoLoadFailed("사용자 정보 로딩에 실패했습니다.");
                });
    }

    private void onUserInfoLoadSuccess() {
        if (btnCreate != null) {
            btnCreate.setEnabled(true);
            btnCreate.setAlpha(1f);
        }
        setUserInfoStatus(null, false);
        Log.d(TAG, "User info loaded: grade=" + userGrade + ", studentId=" + studentId);
    }

    private void onUserInfoLoadFailed(String message) {
        if (btnCreate != null) {
            btnCreate.setEnabled(false);
            btnCreate.setAlpha(0.5f);
        }
        setUserInfoStatus(message, true);
        Log.w(TAG, "User info load failed: " + message);
    }

    private void setUserInfoStatus(String message, boolean visible) {
        if (tvUserInfoStatus == null) return;
        if (visible && message != null) {
            tvUserInfoStatus.setText(message);
            tvUserInfoStatus.setVisibility(View.VISIBLE);
        } else {
            tvUserInfoStatus.setVisibility(View.GONE);
        }
    }

    private void navigateToCreateTimetable() {
        if (!isUserInfoLoaded || userGrade == 0 || studentId.isEmpty()) {
            Toast.makeText(requireContext(),
                    "사용자 정보를 불러오는 중입니다. 잠시 후 다시 시도해주세요.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(requireContext(), HardConstraintActivity.class);
        intent.putExtra("userGrade", userGrade);
        intent.putExtra("studentId", studentId);
        startActivity(intent);
    }

    private void navigateToRecommendation() {
        if (recommendationRequest == null) return;
        Intent intent = new Intent(requireContext(), RecommendationActivity.class);
        intent.putExtra("recommendationRequest", recommendationRequest);
        startActivity(intent);
    }

    private void displayFavoriteTimetables() {
        if (tvFavoriteEmpty == null || favoriteTimetableTable == null ||
                btnFavoriteStar == null || favoriteTimetableScroll == null || favoriteDivider == null) return;

        java.util.List<String> favoriteKeys = preferenceManager.getAllFavoriteTimetableKeys();

        if (favoriteKeys.isEmpty()) {
            // 즐겨찾기된 시간표가 없음
            tvFavoriteEmpty.setVisibility(View.VISIBLE);
            favoriteDivider.setVisibility(View.VISIBLE);
            btnFavoriteStar.setVisibility(View.GONE);
            favoriteTimetableScroll.setVisibility(View.GONE);
            return;
        }

        // 즐겨찾기된 시간표가 있음
        tvFavoriteEmpty.setVisibility(View.GONE);
        favoriteDivider.setVisibility(View.GONE);
        btnFavoriteStar.setVisibility(View.VISIBLE);
        favoriteTimetableScroll.setVisibility(View.VISIBLE);

        // 기존 테이블 내용 제거
        favoriteTimetableTable.removeAllViews();

        Gson gson = new Gson();
        for (String key : favoriteKeys) {
            String timetableJson = preferenceManager.getFavoriteTimetableData(key);
            if (timetableJson != null) {
                try {
                    Timetable timetable = gson.fromJson(timetableJson, Timetable.class);
                    if (timetable != null) {
                        // 시간표를 테이블 그리드에 직접 렌더링
                        com.syu.smarttimetable.ui.recommendation.RecommendationAdapter.renderTimetableGrid(
                                requireContext(),
                                favoriteTimetableTable,
                                timetable
                        );

                        // 별 버튼 클릭 리스너 설정: 해제 클릭 시 삭제
                        btnFavoriteStar.setOnClickListener(v -> {
                            preferenceManager.removeTimetableFavorite(key);
                            displayFavoriteTimetables();
                        });
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing favorite timetable: " + key, e);
                }
            }
        }
    }
}
