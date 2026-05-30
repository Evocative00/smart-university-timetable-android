package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class RecommendationPreferenceManager {

    private static final String TAG = "RecommendationPrefMgr";
    private static final String PREF_NAME = "recommendation_prefs";
    private static final String KEY_FAVORITE_INDEX = "favorite_index";
    private static final String PREFIX_TIMETABLE_FAVORITE = "timetable_fav_";
    private static final String PREFIX_TIMETABLE_DATA = "timetable_data_";
    private static final String KEY_CURRENT_FAVORITE = "current_favorite_timetable_key";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public RecommendationPreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * 즐겨찾기 인덱스 저장 (개별 강의용)
     *
     * @param favoriteIndex -1: 즐겨찾기 없음, 0 이상: 해당 인덱스
     */
    public void saveFavoriteIndex(int favoriteIndex) {
        sharedPreferences.edit()
                .putInt(KEY_FAVORITE_INDEX, favoriteIndex)
                .apply();
    }

    /**
     * 저장된 즐겨찾기 인덱스 로드 (개별 강의용)
     *
     * @return -1: 저장된 즐겨찾기 없음
     */
    public int getFavoriteIndex() {
        return sharedPreferences.getInt(KEY_FAVORITE_INDEX, -1);
    }

    /**
     * 즐겨찾기 설정 초기화 (개별 강의용)
     */
    public void clearFavorite() {
        sharedPreferences.edit()
                .remove(KEY_FAVORITE_INDEX)
                .apply();
    }

    /**
     * 시간표 즐겨찾기 설정 (시간표 전체 데이터와 함께 저장)
     * 단일 즐겨찾기만 허용: 기존 즐겨찾기가 있으면 제거합니다.
     *
     * @param timetableKey "timetable_0", "timetable_1" 등의 형식
     * @param timetableData 시간표 데이터 JSON 문자열
     */
    public void setTimetableFavorite(String timetableKey, String timetableData) {
        // Remove previous favorite if exists and different
        String prev = sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (prev != null && !prev.equals(timetableKey)) {
            editor.remove(PREFIX_TIMETABLE_FAVORITE + prev);
            editor.remove(PREFIX_TIMETABLE_DATA + prev);
        }

        editor.putBoolean(PREFIX_TIMETABLE_FAVORITE + timetableKey, true)
                .putString(PREFIX_TIMETABLE_DATA + timetableKey, timetableData)
                .putString(KEY_CURRENT_FAVORITE, timetableKey)
                .apply();
        Log.d(TAG, "Timetable favorite set: " + timetableKey + " (replaced: " + prev + ")");
    }

    /**
     * 현재 설정된 즐겨찾기 키 조회
     */
    public String getCurrentFavoriteKey() {
        return sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
    }

    /**
     * 시간표 즐겨찾기 제거
     * (현재 즐겨찾기 키를 확인하여 제거 및 현재 키 초기화)
     *
     * @param timetableKey "timetable_0", "timetable_1" 등의 형식
     */
    public void removeTimetableFavorite(String timetableKey) {
        sharedPreferences.edit()
                .remove(PREFIX_TIMETABLE_FAVORITE + timetableKey)
                .remove(PREFIX_TIMETABLE_DATA + timetableKey)
                .apply();

        String cur = sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
        if (cur != null && cur.equals(timetableKey)) {
            sharedPreferences.edit().remove(KEY_CURRENT_FAVORITE).apply();
        }
        Log.d(TAG, "Timetable favorite removed: " + timetableKey);
    }

    /**
     * 즐겨찾기된 시간표 데이터 로드
     *
     * @param timetableKey "timetable_0", "timetable_1" 등의 형식
     * @return 시간표 데이터 JSON 문자열 또는 null
     */
    public String getFavoriteTimetableData(String timetableKey) {
        if (isTimetableFavorite(timetableKey)) {
            return sharedPreferences.getString(PREFIX_TIMETABLE_DATA + timetableKey, null);
        }
        return null;
    }

    /**
     * 시간표 즐겨찾기 여부 확인
     *
     * @param timetableKey "timetable_0", "timetable_1" 등의 형식
     * @return true: 즐겨찾기됨, false: 미즐겨찾기
     */
    public boolean isTimetableFavorite(String timetableKey) {
        return sharedPreferences.getBoolean(PREFIX_TIMETABLE_FAVORITE + timetableKey, false);
    }

    /**
     * 모든 즐겨찾기된 시간표의 키 목록 조회
     */
    public java.util.List<String> getAllFavoriteTimetableKeys() {
        java.util.List<String> keys = new java.util.ArrayList<>();
        String current = getCurrentFavoriteKey();
        if (current != null) keys.add(current);
        return keys;
    }
}
