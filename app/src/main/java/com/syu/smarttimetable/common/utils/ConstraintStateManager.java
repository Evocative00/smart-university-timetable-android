package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConstraintStateManager {

    private static final String PREF_NAME = "constraint_state";
    
    // HardConstraint 키들
    private static final String KEY_TARGET_CREDITS = "target_credits";
    private static final String KEY_FIXED_LECTURES_COUNT = "fixed_lectures_count";
    private static final String KEY_FIXED_LECTURES_PREFIX = "fixed_lectures_";
    private static final String KEY_EXCLUDED_COURSES_COUNT = "excluded_courses_count";
    private static final String KEY_EXCLUDED_COURSES_PREFIX = "excluded_courses_";
    
    // SoftConstraint 키들
    private static final String KEY_PREFERRED_DAYS_COUNT = "preferred_days_count";
    private static final String KEY_PREFERRED_DAYS_PREFIX = "preferred_days_";
    private static final String KEY_FREE_TIME = "free_time";
    private static final String KEY_LUNCH = "lunch";
    private static final String KEY_AVOID_GAP = "avoid_gap";
    private static final String KEY_PROFESSORS = "professors";
    private static final String KEY_TRAVEL_TIME = "travel_time";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ========== HardConstraint 관련 메소드 ==========

    //HardConstraint 상태 저장

    public static void saveHardConstraintState(Context context, String targetCredits,
                                               List<String> fixedLectures,
                                               Set<String> excludedCourses) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        
        // 학점 저장
        editor.putString(KEY_TARGET_CREDITS, targetCredits);
        
        // 고정 강의 저장
        if (fixedLectures != null) {
            editor.putInt(KEY_FIXED_LECTURES_COUNT, fixedLectures.size());
            for (int i = 0; i < fixedLectures.size(); i++) {
                editor.putString(KEY_FIXED_LECTURES_PREFIX + i, fixedLectures.get(i));
            }
        } else {
            editor.putInt(KEY_FIXED_LECTURES_COUNT, 0);
        }
        
        // 제외 강의 저장
        if (excludedCourses != null) {
            List<String> excludedList = new ArrayList<>(excludedCourses);
            editor.putInt(KEY_EXCLUDED_COURSES_COUNT, excludedList.size());
            for (int i = 0; i < excludedList.size(); i++) {
                editor.putString(KEY_EXCLUDED_COURSES_PREFIX + i, excludedList.get(i));
            }
        } else {
            editor.putInt(KEY_EXCLUDED_COURSES_COUNT, 0);
        }
        
        editor.apply();
    }


    public static String getSavedTargetCredits(Context context) {
        return getPreferences(context).getString(KEY_TARGET_CREDITS, "");
    }


    public static List<String> getSavedFixedLectures(Context context) {
        SharedPreferences prefs = getPreferences(context);
        int count = prefs.getInt(KEY_FIXED_LECTURES_COUNT, 0);
        List<String> result = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String lecture = prefs.getString(KEY_FIXED_LECTURES_PREFIX + i, "");
            if (!lecture.isEmpty()) {
                result.add(lecture);
            }
        }
        
        return result;
    }


    public static Set<String> getSavedExcludedCourses(Context context) {
        SharedPreferences prefs = getPreferences(context);
        int count = prefs.getInt(KEY_EXCLUDED_COURSES_COUNT, 0);
        Set<String> result = new HashSet<>();
        
        for (int i = 0; i < count; i++) {
            String course = prefs.getString(KEY_EXCLUDED_COURSES_PREFIX + i, "");
            if (!course.isEmpty()) {
                result.add(course);
            }
        }
        
        return result;
    }



    public static void saveSoftConstraintState(Context context,
                                               List<String> preferredDays,
                                               String freeTime,
                                               boolean lunch,
                                               boolean avoidGap,
                                               String professors,
                                               boolean travelTime) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        
        // 선호 요일 저장
        if (preferredDays != null) {
            editor.putInt(KEY_PREFERRED_DAYS_COUNT, preferredDays.size());
            for (int i = 0; i < preferredDays.size(); i++) {
                editor.putString(KEY_PREFERRED_DAYS_PREFIX + i, preferredDays.get(i));
            }
        } else {
            editor.putInt(KEY_PREFERRED_DAYS_COUNT, 0);
        }
        
        editor.putString(KEY_FREE_TIME, freeTime != null ? freeTime : "");
        editor.putBoolean(KEY_LUNCH, lunch);
        editor.putBoolean(KEY_AVOID_GAP, avoidGap);
        editor.putString(KEY_PROFESSORS, professors != null ? professors : "");
        editor.putBoolean(KEY_TRAVEL_TIME, travelTime);
        
        editor.apply();
    }


    public static List<String> getSavedPreferredDays(Context context) {
        SharedPreferences prefs = getPreferences(context);
        int count = prefs.getInt(KEY_PREFERRED_DAYS_COUNT, 0);
        List<String> result = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String day = prefs.getString(KEY_PREFERRED_DAYS_PREFIX + i, "");
            if (!day.isEmpty()) {
                result.add(day);
            }
        }
        
        return result;
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


    public static void clearAll(Context context) {
        getPreferences(context).edit().clear().apply();
    }


    public static void clearHardConstraintState(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.remove(KEY_TARGET_CREDITS);
        
        // 고정 강의 모두 제거
        int lecturesCount = getPreferences(context).getInt(KEY_FIXED_LECTURES_COUNT, 0);
        for (int i = 0; i < lecturesCount; i++) {
            editor.remove(KEY_FIXED_LECTURES_PREFIX + i);
        }
        editor.remove(KEY_FIXED_LECTURES_COUNT);
        
        // 제외 강의 모두 제거
        int excludedCount = getPreferences(context).getInt(KEY_EXCLUDED_COURSES_COUNT, 0);
        for (int i = 0; i < excludedCount; i++) {
            editor.remove(KEY_EXCLUDED_COURSES_PREFIX + i);
        }
        editor.remove(KEY_EXCLUDED_COURSES_COUNT);
        
        editor.apply();
    }

    // SoftConstraint 상태만 초기화

    public static void clearSoftConstraintState(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        
        // 선호 요일 모두 제거
        int daysCount = getPreferences(context).getInt(KEY_PREFERRED_DAYS_COUNT, 0);
        for (int i = 0; i < daysCount; i++) {
            editor.remove(KEY_PREFERRED_DAYS_PREFIX + i);
        }
        editor.remove(KEY_PREFERRED_DAYS_COUNT);
        
        editor.remove(KEY_FREE_TIME);
        editor.remove(KEY_LUNCH);
        editor.remove(KEY_AVOID_GAP);
        editor.remove(KEY_PROFESSORS);
        editor.remove(KEY_TRAVEL_TIME);
        editor.apply();
    }
}

