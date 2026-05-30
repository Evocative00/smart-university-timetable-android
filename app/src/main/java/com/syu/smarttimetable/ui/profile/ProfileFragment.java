package com.syu.smarttimetable.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.syu.smarttimetable.R;
import com.syu.smarttimetable.common.utils.ProfilePhotoManager;
import com.syu.smarttimetable.data.model.User;
import com.syu.smarttimetable.data.repository.UserRepository;
import com.syu.smarttimetable.ui.auth.LoginActivity;
import com.syu.smarttimetable.ui.main.MainNavigationActivity;
import com.syu.smarttimetable.ui.onboarding.UserInfoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProfileFragment extends Fragment {

    private UserRepository userRepository;
    private User currentUser;

    private MaterialCardView cardNoInfo;
    private MaterialCardView cardUserInfo;
    private ShapeableImageView ivProfilePhoto;
    private TextView tvChangePhoto;
    private TextView tvName;
    private TextView tvDepartment;
    private TextView tvStudentId;
    private MaterialButton btnSetupProfile;
    private MaterialButton btnEditProfile;
    private MaterialButton btnLogout;
    private MaterialButton btnLogoutNoInfo;

    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository();

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK
                            && result.getData() != null) {
                        Uri selectedUri = result.getData().getData();
                        if (selectedUri != null) {
                            handleSelectedPhoto(selectedUri);
                        }
                    }
                }
        );
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
        ivProfilePhoto = view.findViewById(R.id.iv_profile_photo);
        tvChangePhoto = view.findViewById(R.id.tv_change_photo);
        tvName = view.findViewById(R.id.tv_name);
        tvDepartment = view.findViewById(R.id.tv_department);
        tvStudentId = view.findViewById(R.id.tv_student_id);
        btnSetupProfile = view.findViewById(R.id.btn_setup_profile);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogoutNoInfo = view.findViewById(R.id.btn_logout_no_info);

        btnSetupProfile.setOnClickListener(v -> openUserInfoActivity("new"));
        btnEditProfile.setOnClickListener(v -> openUserInfoActivity("edit"));
        btnLogout.setOnClickListener(v -> logout());
        btnLogoutNoInfo.setOnClickListener(v -> logout());

        ivProfilePhoto.setOnClickListener(v -> pickProfilePhoto());
        tvChangePhoto.setOnClickListener(v -> pickProfilePhoto());

        loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void pickProfilePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void handleSelectedPhoto(Uri uri) {
        String path = copyImageToInternalStorage(uri);
        if (path != null) {
            ProfilePhotoManager.savePhotoPath(requireContext(), path);
            loadProfilePhoto();
            updateNavProfileIcon();
            Toast.makeText(requireContext(), "프로필 사진이 변경되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "사진을 불러오지 못했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private String copyImageToInternalStorage(Uri sourceUri) {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return null;

            InputStream in = requireContext().getContentResolver().openInputStream(sourceUri);
            File outFile = new File(requireContext().getFilesDir(), user.getUid() + "_profile.jpg");
            OutputStream out = new FileOutputStream(outFile);
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    private void loadProfilePhoto() {
        if (ivProfilePhoto == null) return;
        String path = ProfilePhotoManager.getPhotoPath(requireContext());
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(path);
                if (bmp != null) {
                    ivProfilePhoto.setImageBitmap(bmp);
                    return;
                }
            }
        }
        ivProfilePhoto.setImageResource(R.drawable.ic_nav_profile);
    }

    private void updateNavProfileIcon() {
        if (getActivity() instanceof MainNavigationActivity) {
            ((MainNavigationActivity) getActivity()).updateProfileIcon();
        }
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

        tvName.setText(user.getName() != null && !user.getName().isEmpty()
                ? user.getName() : "이름 없음");
        tvDepartment.setText(user.getDepartment() != null ? user.getDepartment() : "-");
        tvStudentId.setText("학번 " + (user.getStudentId() != null && !user.getStudentId().isEmpty()
                ? user.getStudentId() : "-"));

        loadProfilePhoto();
    }
}
