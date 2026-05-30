package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Stores the selected profile photo path per Firebase user.
 * The image file itself is copied into the app's internal storage by ProfileFragment.
 */
public final class ProfilePhotoManager {

    private static final String PREF_NAME = "profile_photo_prefs";
    private static final String KEY_PREFIX = "profile_photo_path_";
    private static final String GUEST_KEY = "guest";

    private ProfilePhotoManager() {
    }

    public static void savePhotoPath(Context context, String path) {
        if (context == null || path == null || path.isEmpty()) return;
        getPreferences(context)
                .edit()
                .putString(KEY_PREFIX + getCurrentUserKey(), path)
                .apply();
    }

    public static String getPhotoPath(Context context) {
        if (context == null) return null;
        return getPreferences(context)
                .getString(KEY_PREFIX + getCurrentUserKey(), null);
    }

    public static void clearPhotoPath(Context context) {
        if (context == null) return;
        getPreferences(context)
                .edit()
                .remove(KEY_PREFIX + getCurrentUserKey())
                .apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static String getCurrentUserKey() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : GUEST_KEY;
    }
}
