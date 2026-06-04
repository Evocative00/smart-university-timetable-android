package com.syu.smarttimetable.ui.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.RecommendationPreferenceManager;
import com.syu.smarttimetable.data.model.Lecture;
import com.syu.smarttimetable.data.model.Timetable;
import com.syu.smarttimetable.data.repository.RepresentativeTimetableRepository;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;
import com.syu.smarttimetable.ui.main.MainNavigationActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationAdapter;
import com.syu.smarttimetable.ui.recommendation.TimetablePreviewView;

import java.util.List;

public class TimetableFragment extends Fragment {

    private static final String TAG = "TimetableFragment";

    private RecommendationRequest recommendationRequest;
    private int userGrade = 0;
    private String studentId = "";
    private boolean isUserInfoLoaded = false;
    private boolean primaryActionOpensRecommendation = false;

    private UserRepository userRepository;
    private RecommendationPreferenceManager preferenceManager;
    private RepresentativeTimetableRepository representativeTimetableRepository;

    private MaterialButton btnCreate;
    private MaterialButton btnViewRecommendation;
    private TextView tvTimetableActionTitle;
    private TextView tvTimetableActionDesc;
    private TextView tvUserInfoStatus;
    private TextView tvFavoriteEmpty;
    private ImageButton btnFavoriteStar;
    private View favoriteDivider;
    private TimetablePreviewView favoriteTimetablePreview;

    private Timetable currentFavoriteTimetable;
    private String currentFavoriteKey;
    private String currentFavoriteJson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository();
        representativeTimetableRepository = new RepresentativeTimetableRepository();
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

        preferenceManager = new RecommendationPreferenceManager(requireContext());
        configureFavoriteScope();
        refreshRecommendationRequest();

        btnCreate = view.findViewById(R.id.btn_create_timetable);
        btnViewRecommendation = view.findViewById(R.id.btn_view_recommendation);
        tvTimetableActionTitle = view.findViewById(R.id.tv_timetable_action_title);
        tvTimetableActionDesc = view.findViewById(R.id.tv_timetable_action_desc);
        tvUserInfoStatus = view.findViewById(R.id.tv_user_info_status);
        tvFavoriteEmpty = view.findViewById(R.id.tv_favorite_empty);
        btnFavoriteStar = view.findViewById(R.id.btn_favorite_star);
        favoriteDivider = view.findViewById(R.id.favorite_divider);
        favoriteTimetablePreview = view.findViewById(R.id.favorite_timetable_preview);

        if (btnCreate != null) {
            btnCreate.setOnClickListener(v -> handlePrimaryAction());
        }
        if (btnViewRecommendation != null) {
            btnViewRecommendation.setOnClickListener(v -> handleSecondaryAction());
        }

        displayFavoriteTimetable();
        syncFavoriteFromFirestore();
        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureFavoriteScope();
        refreshRecommendationRequest();
        displayFavoriteTimetable();
        syncFavoriteFromFirestore();
        loadUserInfo();
    }

    private void refreshRecommendationRequest() {
        RecommendationRequest latestRequest = null;
        boolean loadedFromCurrentSession = false;

        if (getActivity() instanceof MainNavigationActivity) {
            latestRequest = ((MainNavigationActivity) getActivity()).getLastRecommendationRequest();
            loadedFromCurrentSession = latestRequest != null;
        }

        if (latestRequest == null && preferenceManager != null) {
            latestRequest = preferenceManager.getRecentRecommendationRequest();
        }

        recommendationRequest = latestRequest;
        if (loadedFromCurrentSession && preferenceManager != null) {
            preferenceManager.saveRecentRecommendationRequest(latestRequest);
        }
        Log.d(TAG, "refreshRecommendationRequest: " + (recommendationRequest != null ? "있음" : "없음"));
    }

    private void updateRecommendationButtonVisibility() {
        updateActionCard();
    }

    private void loadUserInfo() {
        setUserInfoStatus(getString(R.string.timetable_loading_user_info), true);
        updateActionCard();

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
        setUserInfoStatus(null, false);
        updateActionCard();
        Log.d(TAG, "User info loaded: grade=" + userGrade + ", studentId=" + studentId);
    }

    private void onUserInfoLoadFailed(String message) {
        isUserInfoLoaded = false;
        setUserInfoStatus(message, true);
        updateActionCard();
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

    private void updateActionCard() {
        if (btnCreate == null || btnViewRecommendation == null) {
            return;
        }

        boolean hasFavorite = currentFavoriteTimetable != null;
        boolean hasRecentRecommendation = recommendationRequest != null;

        if (hasFavorite) {
            setActionTexts(
                    getString(R.string.timetable_manage_title),
                    getString(R.string.timetable_manage_desc)
            );
            primaryActionOpensRecommendation = false;
            btnCreate.setText(R.string.timetable_new_recommendation_button);
            btnViewRecommendation.setText(R.string.timetable_recent_recommendation_button);
            btnViewRecommendation.setVisibility(hasRecentRecommendation ? View.VISIBLE : View.GONE);
        } else if (hasRecentRecommendation) {
            setActionTexts(
                    getString(R.string.timetable_select_representative_title),
                    getString(R.string.timetable_select_representative_desc)
            );
            primaryActionOpensRecommendation = true;
            btnCreate.setText(R.string.timetable_recent_recommendation_button);
            btnViewRecommendation.setText(R.string.timetable_new_recommendation_button);
            btnViewRecommendation.setVisibility(View.VISIBLE);
        } else {
            setActionTexts(
                    getString(R.string.timetable_create_title),
                    getString(R.string.timetable_create_desc_empty)
            );
            primaryActionOpensRecommendation = false;
            btnCreate.setText(R.string.timetable_create_button);
            btnViewRecommendation.setVisibility(View.GONE);
        }

        updateActionButtonStates();
    }

    private void setActionTexts(String title, String description) {
        if (tvTimetableActionTitle != null) {
            tvTimetableActionTitle.setText(title);
        }
        if (tvTimetableActionDesc != null) {
            tvTimetableActionDesc.setText(description);
        }
    }

    private void updateActionButtonStates() {
        if (btnCreate == null) {
            return;
        }

        boolean createEnabled = isUserInfoLoaded && userGrade > 0 && !studentId.isEmpty();
        boolean primaryEnabled = primaryActionOpensRecommendation
                ? recommendationRequest != null
                : createEnabled;

        btnCreate.setEnabled(primaryEnabled);
        btnCreate.setAlpha(primaryEnabled ? 1f : 0.5f);

        if (btnViewRecommendation != null && btnViewRecommendation.getVisibility() == View.VISIBLE) {
            boolean secondaryOpensCreate = primaryActionOpensRecommendation;
            boolean secondaryEnabled = secondaryOpensCreate ? createEnabled : recommendationRequest != null;
            btnViewRecommendation.setEnabled(secondaryEnabled);
            btnViewRecommendation.setAlpha(secondaryEnabled ? 1f : 0.5f);
        }
    }

    private void handlePrimaryAction() {
        if (primaryActionOpensRecommendation) {
            navigateToRecommendation();
        } else {
            navigateToCreateTimetable();
        }
    }

    private void handleSecondaryAction() {
        if (primaryActionOpensRecommendation) {
            navigateToCreateTimetable();
        } else {
            navigateToRecommendation();
        }
    }

    private void displayFavoriteTimetable() {
        if (preferenceManager == null
                || tvFavoriteEmpty == null
                || btnFavoriteStar == null
                || favoriteDivider == null
                || favoriteTimetablePreview == null) {
            return;
        }

        String favoriteKey = preferenceManager.getCurrentFavoriteKey();
        if (favoriteKey == null) {
            showFavoriteEmptyState();
            return;
        }

        String timetableJson = preferenceManager.getFavoriteTimetableData(favoriteKey);
        if (!isValidFavoriteData(favoriteKey, timetableJson)) {
            showFavoriteEmptyState();
            return;
        }

        try {
            Timetable timetable = new Gson().fromJson(timetableJson, Timetable.class);
            if (timetable == null || timetable.getLecturesReadOnly().isEmpty()) {
                showFavoriteEmptyState();
                return;
            }

            showFavoriteTimetable(favoriteKey, timetableJson, timetable);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing favorite timetable", e);
            showFavoriteEmptyState();
        }
    }

    private void showFavoriteTimetable(String favoriteKey, String timetableJson, Timetable timetable) {
        currentFavoriteKey = favoriteKey;
        currentFavoriteJson = timetableJson;
        currentFavoriteTimetable = timetable;

        tvFavoriteEmpty.setVisibility(View.GONE);
        favoriteDivider.setVisibility(View.GONE);
        btnFavoriteStar.setVisibility(View.VISIBLE);
        favoriteTimetablePreview.setVisibility(View.VISIBLE);

        favoriteTimetablePreview.setTimetable(
                timetable,
                9,
                21,
                this::showLectureDetailSheet
        );

        btnFavoriteStar.setOnClickListener(v -> removeFavoriteTimetableWithUndo(favoriteKey, timetableJson));
        updateActionCard();
    }

    private void showFavoriteEmptyState() {
        currentFavoriteKey = null;
        currentFavoriteJson = null;
        currentFavoriteTimetable = null;

        if (tvFavoriteEmpty != null) {
            tvFavoriteEmpty.setVisibility(View.VISIBLE);
        }
        if (favoriteDivider != null) {
            favoriteDivider.setVisibility(View.VISIBLE);
        }
        if (btnFavoriteStar != null) {
            btnFavoriteStar.setVisibility(View.GONE);
        }
        if (favoriteTimetablePreview != null) {
            favoriteTimetablePreview.setVisibility(View.GONE);
            favoriteTimetablePreview.setTimetable(null, 9, 21, null);
        }
        updateActionCard();
    }


    private void configureFavoriteScope() {
        if (preferenceManager == null || userRepository == null) {
            return;
        }
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        preferenceManager.setCurrentUserId(firebaseUser != null ? firebaseUser.getUid() : null);
    }

    private boolean isCurrentUser(String userId) {
        FirebaseUser currentUser = userRepository != null ? userRepository.getCurrentFirebaseUser() : null;
        return currentUser != null && userId != null && userId.equals(currentUser.getUid());
    }

    private void syncFavoriteFromFirestore() {
        if (preferenceManager == null
                || representativeTimetableRepository == null
                || userRepository == null) {
            return;
        }

        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) {
            return;
        }

        String userId = firebaseUser.getUid();
        configureFavoriteScope();

        String localKey = preferenceManager.getCurrentFavoriteKey();
        String localJson = localKey == null ? null : preferenceManager.getFavoriteTimetableData(localKey);

        representativeTimetableRepository.getRepresentativeTimetable(userId)
                .addOnSuccessListener(doc -> {
                    if (!isAdded() || preferenceManager == null || !isCurrentUser(userId)) return;

                    if (doc != null && doc.exists()) {
                        String remoteKey = doc.getString("timetableKey");
                        String remoteJson = doc.getString("timetableJson");

                        if (isValidFavoriteData(remoteKey, remoteJson)) {
                            preferenceManager.setTimetableFavorite(remoteKey, remoteJson);
                            displayFavoriteTimetable();
                            return;
                        }
                    }

                    if (isValidFavoriteData(localKey, localJson)) {
                        migrateLocalFavoriteToFirestore(userId, localKey, localJson);
                    } else {
                        displayFavoriteTimetable();
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Representative timetable sync failed", e));
    }

    private void migrateLocalFavoriteToFirestore(String userId, String favoriteKey, String timetableJson) {
        if (representativeTimetableRepository == null || !isValidFavoriteData(favoriteKey, timetableJson)) {
            return;
        }

        representativeTimetableRepository
                .saveRepresentativeTimetable(userId, favoriteKey, timetableJson)
                .addOnFailureListener(e -> Log.e(TAG, "Local representative timetable migration failed", e));
    }

    private boolean isValidFavoriteData(String favoriteKey, String timetableJson) {
        return favoriteKey != null
                && !favoriteKey.trim().isEmpty()
                && timetableJson != null
                && !timetableJson.trim().isEmpty();
    }

    private void removeFavoriteTimetableWithUndo(String favoriteKey, String timetableJson) {
        if (preferenceManager == null || !isValidFavoriteData(favoriteKey, timetableJson)) {
            return;
        }

        final boolean[] undoClicked = {false};

        preferenceManager.removeTimetableFavorite(favoriteKey);
        showFavoriteEmptyState();

        Task<Void> remoteDeleteTask = requestFavoriteDeleteFromFirestore(favoriteKey, timetableJson, undoClicked);

        Snackbar snackbar = Snackbar.make(
                        requireView(),
                        getString(R.string.snackbar_representative_removed),
                        Snackbar.LENGTH_LONG
                )
                .setAction(getString(R.string.action_undo), v -> {
                    undoClicked[0] = true;
                    restoreFavoriteTimetable(favoriteKey, timetableJson, remoteDeleteTask);
                });

        snackbar.show();
    }

    private void restoreFavoriteTimetable(String favoriteKey, String timetableJson, Task<Void> remoteDeleteTask) {
        if (preferenceManager == null || !isValidFavoriteData(favoriteKey, timetableJson)) {
            Toast.makeText(requireContext(), getString(R.string.toast_representative_restore_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        preferenceManager.setTimetableFavorite(favoriteKey, timetableJson);
        displayFavoriteTimetable();

        Runnable remoteRestore = () -> {
            FirebaseUser user = userRepository.getCurrentFirebaseUser();
            if (user != null && representativeTimetableRepository != null) {
                representativeTimetableRepository
                        .saveRepresentativeTimetable(user.getUid(), favoriteKey, timetableJson)
                        .addOnFailureListener(e -> Log.e(TAG, "Representative timetable restore sync failed", e));
            }
        };

        if (remoteDeleteTask != null) {
            remoteDeleteTask.addOnCompleteListener(task -> remoteRestore.run());
        } else {
            remoteRestore.run();
        }
    }

    private Task<Void> requestFavoriteDeleteFromFirestore(
            String favoriteKey,
            String timetableJson,
            boolean[] undoClicked
    ) {
        FirebaseUser user = userRepository.getCurrentFirebaseUser();
        if (user == null || representativeTimetableRepository == null) {
            return null;
        }

        return representativeTimetableRepository
                .deleteRepresentativeTimetable(user.getUid())
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Representative timetable delete failed", e);
                    if (undoClicked != null && undoClicked[0]) {
                        return;
                    }
                    if (!isAdded()) {
                        return;
                    }
                    if (preferenceManager != null && preferenceManager.getCurrentFavoriteKey() == null) {
                        preferenceManager.setTimetableFavorite(favoriteKey, timetableJson);
                        displayFavoriteTimetable();
                    }
                    Toast.makeText(
                            requireContext(),
                            getString(R.string.toast_representative_delete_failed),
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }

    private void showLectureDetailSheet(Lecture lecture) {
        if (lecture == null || !isAdded()) {
            return;
        }

        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_lecture_detail, null);

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
        if (recommendationRequest == null) {
            Toast.makeText(requireContext(), R.string.toast_recent_recommendation_missing, Toast.LENGTH_SHORT).show();
            updateActionCard();
            return;
        }
        Intent intent = new Intent(requireContext(), RecommendationActivity.class);
        intent.putExtra("recommendationRequest", recommendationRequest);
        startActivity(intent);
    }
}
