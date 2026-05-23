package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Saves the in-progress hard/soft constraint form state.
 *
 * The preference file is separated by Firebase UID so that one user's unfinished
 * selections do not appear for another user after logout/login on the same device.
 */
public final class ConstraintStateManager {

    private static final String PREF_NAME = "constraint_state";

    private static final String KEY_TARGET_CREDITS = "target_credits";
    private static final String KEY_FIXED_LECTURES_COUNT = "fixed_lectures_count";
    private static final String KEY_FIXED_LECTURES_PREFIX = "fixed_lectures_";
    private static final String KEY_EXCLUDED_COURSES_COUNT = "excluded_courses_count";
    private static final String KEY_EXCLUDED_COURSES_PREFIX = "excluded_courses_";

    private static final String KEY_PREFERRED_DAYS_COUNT = "preferred_days_count";
    private static final String KEY_PREFERRED_DAYS_PREFIX = "preferred_days_";
    private static final String KEY_FREE_TIME = "free_time";
    private static final String KEY_LUNCH = "lunch";
    private static final String KEY_AVOID_GAP = "avoid_gap";
    private static final String KEY_PROFESSORS = "professors";
    private static final String KEY_TRAVEL_TIME = "travel_time";

    private ConstraintStateManager() {
    }

    private static SharedPreferences getPreferences(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ownerKey = user != null && user.getUid() != null ? user.getUid() : "guest";
        return context.getApplicationContext()
                .getSharedPreferences(PREF_NAME + "_" + ownerKey, Context.MODE_PRIVATE);
    }

    public static void saveHardConstraintState(Context context,
                                               String targetCredits,
                                               List<String> fixedLectures,
                                               Set<String> excludedCourses) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        clearList(editor, KEY_FIXED_LECTURES_COUNT, KEY_FIXED_LECTURES_PREFIX,
                prefs.getInt(KEY_FIXED_LECTURES_COUNT, 0));
        clearList(editor, KEY_EXCLUDED_COURSES_COUNT, KEY_EXCLUDED_COURSES_PREFIX,
                prefs.getInt(KEY_EXCLUDED_COURSES_COUNT, 0));

        editor.putString(KEY_TARGET_CREDITS, targetCredits == null ? "" : targetCredits.trim());
        putList(editor, KEY_FIXED_LECTURES_COUNT, KEY_FIXED_LECTURES_PREFIX, fixedLectures);
        putList(editor, KEY_EXCLUDED_COURSES_COUNT, KEY_EXCLUDED_COURSES_PREFIX,
                excludedCourses == null ? null : new ArrayList<>(excludedCourses));
        editor.apply();
    }

    public static String getSavedTargetCredits(Context context) {
        return getPreferences(context).getString(KEY_TARGET_CREDITS, "");
    }

    public static List<String> getSavedFixedLectures(Context context) {
        return getList(getPreferences(context), KEY_FIXED_LECTURES_COUNT, KEY_FIXED_LECTURES_PREFIX);
    }

    public static Set<String> getSavedExcludedCourses(Context context) {
        return new LinkedHashSet<>(
                getList(getPreferences(context), KEY_EXCLUDED_COURSES_COUNT, KEY_EXCLUDED_COURSES_PREFIX)
        );
    }

    public static void saveSoftConstraintState(Context context,
                                               List<String> preferredDays,
                                               String freeTime,
                                               boolean lunch,
                                               boolean avoidGap,
                                               String professors,
                                               boolean travelTime) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        clearList(editor, KEY_PREFERRED_DAYS_COUNT, KEY_PREFERRED_DAYS_PREFIX,
                prefs.getInt(KEY_PREFERRED_DAYS_COUNT, 0));

        putList(editor, KEY_PREFERRED_DAYS_COUNT, KEY_PREFERRED_DAYS_PREFIX, preferredDays);
        editor.putString(KEY_FREE_TIME, freeTime == null ? "" : freeTime);
        editor.putBoolean(KEY_LUNCH, lunch);
        editor.putBoolean(KEY_AVOID_GAP, avoidGap);
        editor.putString(KEY_PROFESSORS, professors == null ? "" : professors.trim());
        editor.putBoolean(KEY_TRAVEL_TIME, travelTime);
        editor.apply();
    }

    public static List<String> getSavedPreferredDays(Context context) {
        return getList(getPreferences(context), KEY_PREFERRED_DAYS_COUNT, KEY_PREFERRED_DAYS_PREFIX);
    }

    public static String getSavedFreeTime(Context context) {
        return getPreferences(context).getString(KEY_FREE_TIME, "");
    }

    public static boolean getSavedLunch(Context context) {
        return getPreferences(context).getBoolean(KEY_LUNCH, false);
    }

    public static boolean getSavedAvoidGap(Context context) {
        return getPreferences(context).getBoolean(KEY_AVOID_GAP, false);
    }

    public static String getSavedProfessors(Context context) {
        return getPreferences(context).getString(KEY_PROFESSORS, "");
    }

    public static boolean getSavedTravelTime(Context context) {
        return getPreferences(context).getBoolean(KEY_TRAVEL_TIME, false);
    }

    public static void clearHardConstraintState(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_TARGET_CREDITS);
        clearList(editor, KEY_FIXED_LECTURES_COUNT, KEY_FIXED_LECTURES_PREFIX,
                prefs.getInt(KEY_FIXED_LECTURES_COUNT, 0));
        clearList(editor, KEY_EXCLUDED_COURSES_COUNT, KEY_EXCLUDED_COURSES_PREFIX,
                prefs.getInt(KEY_EXCLUDED_COURSES_COUNT, 0));
        editor.apply();
    }

    public static void clearSoftConstraintState(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        clearList(editor, KEY_PREFERRED_DAYS_COUNT, KEY_PREFERRED_DAYS_PREFIX,
                prefs.getInt(KEY_PREFERRED_DAYS_COUNT, 0));
        editor.remove(KEY_FREE_TIME);
        editor.remove(KEY_LUNCH);
        editor.remove(KEY_AVOID_GAP);
        editor.remove(KEY_PROFESSORS);
        editor.remove(KEY_TRAVEL_TIME);
        editor.apply();
    }

    public static void clearAll(Context context) {
        getPreferences(context).edit().clear().apply();
    }

    private static void putList(SharedPreferences.Editor editor,
                                String countKey,
                                String itemPrefix,
                                List<String> items) {
        if (items == null || items.isEmpty()) {
            editor.putInt(countKey, 0);
            return;
        }

        int savedCount = 0;
        for (String item : items) {
            if (item == null || item.trim().isEmpty()) {
                continue;
            }
            editor.putString(itemPrefix + savedCount, item.trim());
            savedCount++;
        }
        editor.putInt(countKey, savedCount);
    }

    private static List<String> getList(SharedPreferences prefs, String countKey, String itemPrefix) {
        int count = prefs.getInt(countKey, 0);
        List<String> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String item = prefs.getString(itemPrefix + i, "");
            if (item != null && !item.trim().isEmpty()) {
                result.add(item.trim());
            }
        }

        return result;
    }

    private static void clearList(SharedPreferences.Editor editor,
                                  String countKey,
                                  String itemPrefix,
                                  int count) {
        for (int i = 0; i < count; i++) {
            editor.remove(itemPrefix + i);
        }
        editor.remove(countKey);
    }
}
