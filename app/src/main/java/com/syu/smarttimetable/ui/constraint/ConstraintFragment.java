package com.syu.smarttimetable.ui.constraint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.repository.UserRepository;

public class ConstraintFragment extends Fragment {

    private int userGrade = 0;
    private String studentId = "";
    private UserRepository userRepository;

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
        return inflater.inflate(R.layout.fragment_constraint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnHard = view.findViewById(R.id.btn_hard_constraint);
        Button btnSoft = view.findViewById(R.id.btn_soft_constraint);

        btnHard.setOnClickListener(v -> navigateToHardConstraint());
        btnSoft.setOnClickListener(v -> {
            Toast.makeText(
                    requireContext(),
                    "선택 조건은 필수 조건 설정 후 이어서 설정할 수 있습니다.",
                    Toast.LENGTH_SHORT
            ).show();
            navigateToHardConstraint();
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
        if (firebaseUser == null) {
            userGrade = 0;
            studentId = "";
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(doc -> {
                    if (doc == null || !doc.exists()) return;
                    Long grade = doc.getLong("grade");
                    String sid = doc.getString("studentId");
                    if (grade != null) userGrade = grade.intValue();
                    if (sid != null) studentId = sid;
                });
    }

    private void navigateToHardConstraint() {
        Intent intent = new Intent(requireContext(), HardConstraintActivity.class);
        intent.putExtra("userGrade", userGrade);
        intent.putExtra("studentId", studentId);
        startActivity(intent);
    }
}
