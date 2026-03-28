package com.syu.smarttimetable.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;

public class UserInfoActivity extends AppCompatActivity {

    private AutoCompleteTextView autoDepartment;
    private AutoCompleteTextView autoMajorDetail;
    private AutoCompleteTextView autoGrade;
    private TextInputEditText etStudentId;
    private TextInputLayout layoutMajorDetail;
    private Button btnSave;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userRepository = new UserRepository();

        bindViews();
        setupDepartmentDropdown();
        setupGradeDropdown();
        setupSaveButton();
    }

    private void bindViews() {
        autoDepartment = findViewById(R.id.auto_department);
        autoMajorDetail = findViewById(R.id.auto_major_detail);
        autoGrade = findViewById(R.id.auto_grade);
        etStudentId = findViewById(R.id.et_student_id);
        layoutMajorDetail = findViewById(R.id.layout_major_detail);
        btnSave = findViewById(R.id.btn_save);
    }

    private void setupDepartmentDropdown() {
        String[] departments = getResources().getStringArray(R.array.department_array);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                departments
        );

        autoDepartment.setAdapter(departmentAdapter);
        autoDepartment.setOnClickListener(v -> autoDepartment.showDropDown());

        autoDepartment.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDepartment = (String) parent.getItemAtPosition(position);
            updateMajorDetailUI(selectedDepartment);
        });
    }

    private void setupGradeDropdown() {
        String[] grades = getResources().getStringArray(R.array.grade_array);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                grades
        );

        autoGrade.setAdapter(gradeAdapter);
        autoGrade.setOnClickListener(v -> autoGrade.showDropDown());
    }

    private void updateMajorDetailUI(String department) {
        String[] majorDetails = DepartmentMajorMapper.getMajorDetails(department);

        if (majorDetails.length > 0) {
            layoutMajorDetail.setVisibility(android.view.View.VISIBLE);

            ArrayAdapter<String> detailAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    majorDetails
            );

            autoMajorDetail.setText("", false);
            autoMajorDetail.setAdapter(detailAdapter);
            autoMajorDetail.setOnClickListener(v -> autoMajorDetail.showDropDown());
        } else {
            layoutMajorDetail.setVisibility(android.view.View.GONE);
            autoMajorDetail.setText("", false);
        }
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> saveUserInfo());
    }

    private void saveUserInfo() {
        FirebaseUser firebaseUser = userRepository.getCurrentFirebaseUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String department = getText(autoDepartment);
        String majorDetail = getText(autoMajorDetail);
        String gradeText = getText(autoGrade);
        String studentId = getText(etStudentId);

        if (TextUtils.isEmpty(department)) {
            Toast.makeText(this, "학과를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (DepartmentMajorMapper.hasMajorDetails(department) && TextUtils.isEmpty(majorDetail)) {
            Toast.makeText(this, "세부전공을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(gradeText)) {
            Toast.makeText(this, "학년을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(studentId)) {
            Toast.makeText(this, "학번을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int grade = parseGrade(gradeText);
        if (grade <= 0) {
            Toast.makeText(this, "학년 값이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.getUser(firebaseUser.getUid())
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);

                    if (user == null) {
                        user = new User();
                        user.setUserId(firebaseUser.getUid());
                        user.setEmail(firebaseUser.getEmail());
                    }

                    user.setDepartment(department);
                    user.setMajorDetail(majorDetail);
                    user.setGrade(grade);
                    user.setStudentId(studentId);

                    userRepository.updateUserInfo(user)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "사용자 정보 저장 완료", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(this, HardConstraintActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "사용자 정보를 불러오지 못했습니다: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private String getText(android.widget.TextView view) {
        return view.getText() == null ? "" : view.getText().toString().trim();
    }

    private int parseGrade(String gradeText) {
        try {
            return Integer.parseInt(gradeText.replace("학년", "").trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}