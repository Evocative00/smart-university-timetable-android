package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecommendationPreferenceManager {

    private static final String TAG = "RecommendationPrefMgr";
    private static final String PREF_NAME = "recommendation_prefs";
    private static final String KEY_FAVORITE_INDEX = "favorite_index";
    private static final String PREFIX_TIMETABLE_FAVORITE = "timetable_fav_";
    private static final String PREFIX_TIMETABLE_DATA = "timetable_data_";
    private static final String KEY_CURRENT_FAVORITE = "current_favorite_timetable_key";

    private final SharedPreferences sharedPreferences;

    public RecommendationPreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 즐겨찾기 인덱스 저장. -1이면 즐겨찾기 없음, 0 이상이면 해당 인덱스가 즐겨찾기입니다.
     */
    public void saveFavoriteIndex(int favoriteIndex) {
        sharedPreferences.edit()
                .putInt(KEY_FAVORITE_INDEX, favoriteIndex)
                .apply();
    }

    /**
     * 저장된 즐겨찾기 인덱스를 불러옵니다. 저장값이 없으면 -1을 반환합니다.
     */
    public int getFavoriteIndex() {
        return sharedPreferences.getInt(KEY_FAVORITE_INDEX, -1);
    }

    /**
     * 즐겨찾기 인덱스 저장값을 초기화합니다.
     */
    public void clearFavorite() {
        sharedPreferences.edit()
                .remove(KEY_FAVORITE_INDEX)
                .apply();
    }

    /**
     * 시간표 즐겨찾기를 저장합니다. 앱 정책상 한 번에 하나의 시간표만 즐겨찾기됩니다.
     */
    public void setTimetableFavorite(String timetableKey, String timetableData) {
        String previousKey = sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (previousKey != null && !previousKey.equals(timetableKey)) {
            editor.remove(PREFIX_TIMETABLE_FAVORITE + previousKey);
            editor.remove(PREFIX_TIMETABLE_DATA + previousKey);
        }

        editor.putBoolean(PREFIX_TIMETABLE_FAVORITE + timetableKey, true)
                .putString(PREFIX_TIMETABLE_DATA + timetableKey, timetableData)
                .putString(KEY_CURRENT_FAVORITE, timetableKey)
                .apply();

        Log.d(TAG, "Timetable favorite set: " + timetableKey + " (previous=" + previousKey + ")");
    }

    public String getCurrentFavoriteKey() {
        return sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
    }

    public void removeTimetableFavorite(String timetableKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit()
                .remove(PREFIX_TIMETABLE_FAVORITE + timetableKey)
                .remove(PREFIX_TIMETABLE_DATA + timetableKey);

        String currentKey = sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
        if (currentKey != null && currentKey.equals(timetableKey)) {
            editor.remove(KEY_CURRENT_FAVORITE);
        }

        editor.apply();
        Log.d(TAG, "Timetable favorite removed: " + timetableKey);
    }

    public String getFavoriteTimetableData(String timetableKey) {
        if (!isTimetableFavorite(timetableKey)) {
            return null;
        }
        return sharedPreferences.getString(PREFIX_TIMETABLE_DATA + timetableKey, null);
    }

    public boolean isTimetableFavorite(String timetableKey) {
        return sharedPreferences.getBoolean(PREFIX_TIMETABLE_FAVORITE + timetableKey, false);
    }

    public List<String> getAllFavoriteTimetableKeys() {
        List<String> keys = new ArrayList<>();
        String currentKey = getCurrentFavoriteKey();
        if (currentKey != null) {
            keys.add(currentKey);
        }
        return keys;
    }
}
