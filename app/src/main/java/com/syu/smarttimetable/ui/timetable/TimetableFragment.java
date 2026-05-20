package com.syu.smarttimetable.ui.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;
import com.syu.smarttimetable.ui.main.MainNavigationActivity;
import com.syu.smarttimetable.ui.recommendation.RecommendationActivity;

public class TimetableFragment extends Fragment {

    private static final String TAG = "TimetableFragment";

    private RecommendationRequest recommendationRequest;
    private int userGrade = 0;
    private String studentId = "";
    private UserRepository userRepository;
    private Button btnViewRecommendation;

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

        Button btnCreate = view.findViewById(R.id.btn_create_timetable);
        btnViewRecommendation = view.findViewById(R.id.btn_view_recommendation);

        btnCreate.setOnClickListener(v -> navigateToCreateTimetable());
        btnViewRecommendation.setOnClickListener(v -> navigateToRecommendation());

        updateButtonVisibility();
        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 탭 전환 없이 Activity로 복귀할 때도 최신 값으로 갱신
        refreshRecommendationRequest();
        updateButtonVisibility();
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

    private void updateButtonVisibility() {
        if (btnViewRecommendation == null) return;
        btnViewRecommendation.setVisibility(
                recommendationRequest != null ? View.VISIBLE : View.GONE
        );
        Log.d(TAG, "updateButtonVisibility: " + (recommendationRequest != null ? "VISIBLE" : "GONE"));
    }

    private void loadUserInfo() {
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) return;

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(doc -> {
                    if (doc == null || !doc.exists()) return;
                    Long grade = doc.getLong("grade");
                    String sid = doc.getString("studentId");
                    if (grade != null) userGrade = grade.intValue();
                    if (sid != null) studentId = sid;
                });
    }

    private void navigateToCreateTimetable() {
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
}
