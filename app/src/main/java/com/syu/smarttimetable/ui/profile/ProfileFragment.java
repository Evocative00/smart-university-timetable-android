package com.syu.smarttimetable.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.auth.LoginActivity;
import com.syu.smarttimetable.ui.onboarding.UserInfoActivity;

public class ProfileFragment extends Fragment {

    private UserRepository userRepository;
    private User currentUser;

    // Views
    private MaterialCardView cardNoInfo;
    private MaterialCardView cardUserInfo;
    private TextView tvName;
    private TextView tvDepartment;
    private TextView tvMajorDetail;
    private TextView tvGrade;
    private TextView tvStudentId;
    private MaterialButton btnSetupProfile;
    private MaterialButton btnEditProfile;
    private MaterialButton btnLogout;
    private MaterialButton btnLogoutNoInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardNoInfo = view.findViewById(R.id.card_no_info);
        cardUserInfo = view.findViewById(R.id.card_user_info);
        tvName = view.findViewById(R.id.tv_name);
        tvDepartment = view.findViewById(R.id.tv_department);
        tvMajorDetail = view.findViewById(R.id.tv_major_detail);
        tvGrade = view.findViewById(R.id.tv_grade);
        tvStudentId = view.findViewById(R.id.tv_student_id);
        btnSetupProfile = view.findViewById(R.id.btn_setup_profile);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogoutNoInfo = view.findViewById(R.id.btn_logout_no_info);

        btnSetupProfile.setOnClickListener(v -> openUserInfoActivity("new"));
        btnEditProfile.setOnClickListener(v -> openUserInfoActivity("edit"));

        btnLogout.setOnClickListener(v -> logout());
        btnLogoutNoInfo.setOnClickListener(v -> logout());

        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void openUserInfoActivity(String mode) {
        Intent intent = new Intent(requireContext(), UserInfoActivity.class);
        intent.putExtra("mode", mode);
        if ("edit".equals(mode) && currentUser != null) {
            intent.putExtra("user", currentUser);
        }
        startActivity(intent);
    }

    private void logout() {
        userRepository.logout();
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loadUserInfo() {
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) {
            showNoInfoState();
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(doc -> {
                    if (getView() == null) return;

                    if (doc == null || !doc.exists()) {
                        showNoInfoState();
                        return;
                    }

                    User user = doc.toObject(User.class);
                    if (user == null || !hasBasicInfo(user)) {
                        showNoInfoState();
                        return;
                    }

                    currentUser = user;
                    showUserInfoState(user);
                })
                .addOnFailureListener(e -> {
                    if (getView() == null) return;
                    showNoInfoState();
                });
    }

    /** 기본 정보가 실제로 입력되어 있는지 확인 */
    private boolean hasBasicInfo(User user) {
        return user.getDepartment() != null && !user.getDepartment().isEmpty();
    }

    private void showNoInfoState() {
        cardNoInfo.setVisibility(View.VISIBLE);
        cardUserInfo.setVisibility(View.GONE);
        btnLogoutNoInfo.setVisibility(View.VISIBLE);
    }

    private void showUserInfoState(User user) {
        cardNoInfo.setVisibility(View.GONE);
        cardUserInfo.setVisibility(View.VISIBLE);
        btnLogoutNoInfo.setVisibility(View.GONE);

        tvName.setText("이름: " + (user.getName() != null && !user.getName().isEmpty()
                ? user.getName() : "-"));
        tvDepartment.setText("학과: " + (user.getDepartment() != null ? user.getDepartment() : "-"));
        tvMajorDetail.setText("세부전공: " + (user.getMajorDetail() != null && !user.getMajorDetail().isEmpty()
                ? user.getMajorDetail() : "-"));
        tvGrade.setText("학년: " + (user.getGrade() > 0 ? user.getGrade() + "학년" : "-"));
        tvStudentId.setText("학번: " + (user.getStudentId() != null && !user.getStudentId().isEmpty()
                ? user.getStudentId() : "-"));
    }
}
