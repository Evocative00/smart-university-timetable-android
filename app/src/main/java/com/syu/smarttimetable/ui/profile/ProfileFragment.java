package com.syu.smarttimetable.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.auth.LoginActivity;
import com.syu.smarttimetable.ui.onboarding.UserInfoActivity;

public class ProfileFragment extends Fragment {

    private UserRepository userRepository;
    private TextView tvDepartment, tvMajorDetail, tvGrade, tvStudentId;

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

        tvDepartment = view.findViewById(R.id.tv_department);
        tvMajorDetail = view.findViewById(R.id.tv_major_detail);
        tvGrade = view.findViewById(R.id.tv_grade);
        tvStudentId = view.findViewById(R.id.tv_student_id);

        Button btnEdit = view.findViewById(R.id.btn_edit_profile);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UserInfoActivity.class);
            intent.putExtra("mode", "edit");
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            userRepository.logout();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void loadUserInfo() {
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();
        if (firebaseUser == null) return;

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(doc -> {
                    if (doc == null || !doc.exists() || getView() == null) return;
                    User user = doc.toObject(User.class);
                    if (user == null) return;

                    tvDepartment.setText("학과: " + (user.getDepartment() != null ? user.getDepartment() : "-"));
                    tvMajorDetail.setText("세부전공: " + (user.getMajorDetail() != null ? user.getMajorDetail() : "-"));
                    tvGrade.setText("학년: " + (user.getGrade() > 0 ? user.getGrade() + "학년" : "-"));
                    tvStudentId.setText("학번: " + (user.getStudentId() != null ? user.getStudentId() : "-"));
                });
    }
}
