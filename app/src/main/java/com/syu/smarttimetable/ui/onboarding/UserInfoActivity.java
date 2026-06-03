package com.syu.smarttimetable.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.auth.LoginActivity;
import com.syu.smarttimetable.ui.constraint.HardConstraintActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView autoDepartment;
    private MaterialAutoCompleteTextView autoMajorDetail;
    private MaterialAutoCompleteTextView autoGrade;
    private TextInputEditText etName;
    private TextInputEditText etStudentId;
    private TextInputLayout layoutMajorDetail;
    private Button btnSave;

    private UserRepository userRepository;
    private String mode = "new";
    private User existingUser = null;

    private interface SelectionCallback {
        void onSelected(String item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userRepository = new UserRepository();

        mode = getIntent().getStringExtra("mode");
        if (mode == null) {
            mode = "new";
        }

        if (getIntent().hasExtra("user")) {
            existingUser = (User) getIntent().getSerializableExtra("user");
        }

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if ("edit".equals(mode)) {
                finish();
            } else {
                userRepository.logout();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        bindViews();
        setupDepartmentDropdown();
        setupGradeDropdown();
        setupSaveButton();

        if ("edit".equals(mode) && existingUser != null) {
            loadExistingInfo();
        }
    }

    private void bindViews() {
        etName = findViewById(R.id.et_name);
        autoDepartment = findViewById(R.id.auto_department);
        autoMajorDetail = findViewById(R.id.auto_major_detail);
        autoGrade = findViewById(R.id.auto_grade);
        etStudentId = findViewById(R.id.et_student_id);
        layoutMajorDetail = findViewById(R.id.layout_major_detail);
        btnSave = findViewById(R.id.btn_save);
    }

    private void loadExistingInfo() {
        if (existingUser == null) {
            return;
        }

        if (existingUser.getName() != null) {
            etName.setText(existingUser.getName());
        }

        if (existingUser.getDepartment() != null) {
            autoDepartment.setText(existingUser.getDepartment(), false);
        }

        if (existingUser.getGrade() > 0) {
            autoGrade.setText(existingUser.getGrade() + "학년", false);
        }

        if (existingUser.getStudentId() != null) {
            etStudentId.setText(existingUser.getStudentId());
        }

        if (existingUser.getDepartment() != null) {
            updateMajorDetailUI(existingUser.getDepartment(), existingUser.getMajorDetail());
        }
    }

    private void setupDepartmentDropdown() {
        String[] departments = getResources().getStringArray(R.array.department_array);

        if (TextUtils.isEmpty(getText(autoDepartment))) {
            autoDepartment.setText("", false);
        }

        autoDepartment.setFocusable(false);
        autoDepartment.setFocusableInTouchMode(false);

        autoDepartment.setOnClickListener(v ->
                showSearchDialog(
                        "학과 검색",
                        "학과명을 입력해주세요",
                        departments,
                        selectedDepartment -> {
                            autoDepartment.setText(selectedDepartment, false);
                            updateMajorDetailUI(selectedDepartment);
                        }
                )
        );
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
        updateMajorDetailUI(department, null);
    }

    private void updateMajorDetailUI(String department, String existingMajorDetail) {
        String[] majorDetails = DepartmentMajorMapper.getMajorDetails(department);

        if (majorDetails.length > 0) {
            layoutMajorDetail.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(getText(autoMajorDetail)) && TextUtils.isEmpty(existingMajorDetail)) {
                autoMajorDetail.setText("", false);
            }

            autoMajorDetail.setFocusable(false);
            autoMajorDetail.setFocusableInTouchMode(false);

            autoMajorDetail.setOnClickListener(v ->
                    showSearchDialog(
                            "세부전공 검색",
                            "세부전공명을 입력해주세요",
                            majorDetails,
                            selectedMajor -> autoMajorDetail.setText(selectedMajor, false)
                    )
            );

            if (!TextUtils.isEmpty(existingMajorDetail)) {
                autoMajorDetail.setText(existingMajorDetail, false);
            }
        } else {
            layoutMajorDetail.setVisibility(View.GONE);
            autoMajorDetail.setText("", false);
        }
    }

    private void showSearchDialog(String title,
                                  String hint,
                                  String[] items,
                                  SelectionCallback callback) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_search, null);

        EditText etSearch = dialogView.findViewById(R.id.et_search);
        ListView lvItems = dialogView.findViewById(R.id.lv_lectures);

        etSearch.setHint(hint);

        List<String> allItems = new ArrayList<>(Arrays.asList(items));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(allItems)
        );

        lvItems.setAdapter(adapter);

        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setView(dialogView)
                .setNegativeButton("닫기", null)
                .create();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 사용 안 함
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s == null ? "" : s.toString().toLowerCase().trim();

                adapter.clear();

                for (String item : allItems) {
                    if (item != null && item.toLowerCase().contains(query)) {
                        adapter.add(item);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 사용 안 함
            }
        });

        lvItems.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);

            if (callback != null) {
                callback.onSelected(selected);
            }

            dialog.dismiss();
        });

        dialog.show();
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

        String name = getText(etName);
        String department = getText(autoDepartment);
        String majorDetail = getText(autoMajorDetail);
        String gradeText = getText(autoGrade);
        String studentId = getText(etStudentId);

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

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

                    user.setName(name);
                    user.setDepartment(department);
                    user.setMajorDetail(majorDetail);
                    user.setGrade(grade);
                    user.setStudentId(studentId);

                    userRepository.updateUserInfo(user)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "사용자 정보 저장 완료", Toast.LENGTH_SHORT).show();

                                if ("edit".equals(mode)) {
                                    finish();
                                } else {
                                    Intent intent = new Intent(this,
                                            com.syu.smarttimetable.ui.main.MainNavigationActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
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