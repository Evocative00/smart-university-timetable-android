package com.syu.smarttimetable.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.syu.smarttimetable.domain.recommendation.RecommendationRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RecommendationPreferenceManager {

    private static final String TAG = "RecommendationPrefMgr";
    private static final String PREF_NAME = "recommendation_prefs";
    private static final String KEY_FAVORITE_INDEX = "favorite_index";
    private static final String PREFIX_TIMETABLE_FAVORITE = "timetable_fav_";
    private static final String PREFIX_TIMETABLE_DATA = "timetable_data_";
    private static final String KEY_CURRENT_FAVORITE = "current_favorite_timetable_key";
    private static final String KEY_RECENT_RECOMMENDATION_REQUEST = "recent_recommendation_request";
    private static final String KEY_LEGACY_FAVORITE_OWNER_UID = "legacy_favorite_owner_uid";
    private static final String GUEST_SCOPE = "guest";

    private final SharedPreferences sharedPreferences;
    private String currentUserId;

    public RecommendationPreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 대표 시간표 캐시를 로그인 사용자별로 분리하기 위한 현재 사용자 범위를 설정합니다.
     * 기존 로컬 즐겨찾기가 있으면 처음 로그인한 사용자 소유로 한 번만 승격합니다.
     */
    public void setCurrentUserId(String userId) {
        currentUserId = normalizeUserId(userId);
        migrateLegacyFavoriteForCurrentUserIfNeeded();
    }

    public String getCurrentUserId() {
        return currentUserId;
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
        if (isBlank(timetableKey) || timetableData == null) {
            Log.w(TAG, "Ignored favorite save because key/data is empty.");
            return;
        }

        String previousKey = sharedPreferences.getString(currentFavoriteKeyName(), null);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (previousKey != null && !previousKey.equals(timetableKey)) {
            editor.remove(favoriteFlagKey(previousKey));
            editor.remove(timetableDataKey(previousKey));
        }

        editor.putBoolean(favoriteFlagKey(timetableKey), true)
                .putString(timetableDataKey(timetableKey), timetableData)
                .putString(currentFavoriteKeyName(), timetableKey)
                .apply();

        Log.d(TAG, "Timetable favorite set: " + timetableKey
                + " (previous=" + previousKey + ", scope=" + activeScope() + ")");
    }

    public String getCurrentFavoriteKey() {
        return sharedPreferences.getString(currentFavoriteKeyName(), null);
    }

    public void removeTimetableFavorite(String timetableKey) {
        if (isBlank(timetableKey)) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit()
                .remove(favoriteFlagKey(timetableKey))
                .remove(timetableDataKey(timetableKey));

        String currentKey = sharedPreferences.getString(currentFavoriteKeyName(), null);
        if (currentKey != null && currentKey.equals(timetableKey)) {
            editor.remove(currentFavoriteKeyName());
        }

        editor.apply();
        Log.d(TAG, "Timetable favorite removed: " + timetableKey + " (scope=" + activeScope() + ")");
    }

    public String getFavoriteTimetableData(String timetableKey) {
        if (!isTimetableFavorite(timetableKey)) {
            return null;
        }
        return sharedPreferences.getString(timetableDataKey(timetableKey), null);
    }

    public boolean isTimetableFavorite(String timetableKey) {
        if (isBlank(timetableKey)) {
            return false;
        }
        return sharedPreferences.getBoolean(favoriteFlagKey(timetableKey), false);
    }

    public List<String> getAllFavoriteTimetableKeys() {
        List<String> keys = new ArrayList<>();
        String currentKey = getCurrentFavoriteKey();
        if (currentKey != null) {
            keys.add(currentKey);
        }
        return keys;
    }

    /**
     * 최근 추천 조건을 사용자별 로컬 캐시에 저장합니다.
     * 추천 결과 전체가 아니라 조건만 저장해서 앱 재시작 후 최신 강의 데이터로 다시 계산할 수 있게 합니다.
     */
    public void saveRecentRecommendationRequest(RecommendationRequest request) {
        if (request == null) {
            clearRecentRecommendationRequest();
            return;
        }

        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(request);
            objectStream.close();

            String encoded = Base64.encodeToString(byteStream.toByteArray(), Base64.NO_WRAP);
            sharedPreferences.edit()
                    .putString(recentRecommendationRequestKeyName(), encoded)
                    .apply();

            Log.d(TAG, "Recent recommendation request saved. scope=" + activeScope());
        } catch (Exception e) {
            Log.e(TAG, "Failed to save recent recommendation request", e);
        }
    }

    public RecommendationRequest getRecentRecommendationRequest() {
        String encoded = sharedPreferences.getString(recentRecommendationRequestKeyName(), null);
        if (isBlank(encoded)) {
            return null;
        }

        try {
            byte[] bytes = Base64.decode(encoded, Base64.DEFAULT);
            ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = objectStream.readObject();
            objectStream.close();

            if (object instanceof RecommendationRequest) {
                return (RecommendationRequest) object;
            }

            clearRecentRecommendationRequest();
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to read recent recommendation request", e);
            clearRecentRecommendationRequest();
            return null;
        }
    }

    public boolean hasRecentRecommendationRequest() {
        return getRecentRecommendationRequest() != null;
    }

    public void clearRecentRecommendationRequest() {
        sharedPreferences.edit()
                .remove(recentRecommendationRequestKeyName())
                .apply();
    }

    private void migrateLegacyFavoriteForCurrentUserIfNeeded() {
        if (currentUserId == null) {
            return;
        }

        String scopedCurrentKeyName = currentFavoriteKeyNameForScope(currentUserId);
        if (sharedPreferences.contains(scopedCurrentKeyName)) {
            return;
        }

        String legacyKey = sharedPreferences.getString(KEY_CURRENT_FAVORITE, null);
        if (isBlank(legacyKey)) {
            return;
        }

        String legacyOwner = normalizeUserId(sharedPreferences.getString(KEY_LEGACY_FAVORITE_OWNER_UID, null));
        if (legacyOwner != null && !legacyOwner.equals(currentUserId)) {
            return;
        }

        String legacyData = sharedPreferences.getString(PREFIX_TIMETABLE_DATA + legacyKey, null);
        boolean legacyFavorite = sharedPreferences.getBoolean(PREFIX_TIMETABLE_FAVORITE + legacyKey, false);
        if (!legacyFavorite || isBlank(legacyData)) {
            return;
        }

        sharedPreferences.edit()
                .putString(KEY_LEGACY_FAVORITE_OWNER_UID, currentUserId)
                .putBoolean(favoriteFlagKeyForScope(currentUserId, legacyKey), true)
                .putString(timetableDataKeyForScope(currentUserId, legacyKey), legacyData)
                .putString(scopedCurrentKeyName, legacyKey)
                .remove(PREFIX_TIMETABLE_FAVORITE + legacyKey)
                .remove(PREFIX_TIMETABLE_DATA + legacyKey)
                .remove(KEY_CURRENT_FAVORITE)
                .apply();

        Log.d(TAG, "Migrated legacy favorite timetable to user scope: " + currentUserId);
    }

    private String currentFavoriteKeyName() {
        return currentFavoriteKeyNameForScope(activeScope());
    }

    private String favoriteFlagKey(String timetableKey) {
        return favoriteFlagKeyForScope(activeScope(), timetableKey);
    }

    private String timetableDataKey(String timetableKey) {
        return timetableDataKeyForScope(activeScope(), timetableKey);
    }

    private String recentRecommendationRequestKeyName() {
        String scope = activeScope();
        if (GUEST_SCOPE.equals(scope)) {
            return KEY_RECENT_RECOMMENDATION_REQUEST;
        }
        return KEY_RECENT_RECOMMENDATION_REQUEST + "_" + scope;
    }

    private String currentFavoriteKeyNameForScope(String scope) {
        if (GUEST_SCOPE.equals(scope)) {
            return KEY_CURRENT_FAVORITE;
        }
        return KEY_CURRENT_FAVORITE + "_" + scope;
    }

    private String favoriteFlagKeyForScope(String scope, String timetableKey) {
        if (GUEST_SCOPE.equals(scope)) {
            return PREFIX_TIMETABLE_FAVORITE + timetableKey;
        }
        return PREFIX_TIMETABLE_FAVORITE + scope + "_" + timetableKey;
    }

    private String timetableDataKeyForScope(String scope, String timetableKey) {
        if (GUEST_SCOPE.equals(scope)) {
            return PREFIX_TIMETABLE_DATA + timetableKey;
        }
        return PREFIX_TIMETABLE_DATA + scope + "_" + timetableKey;
    }

    private String activeScope() {
        return currentUserId != null ? currentUserId : GUEST_SCOPE;
    }

    private String normalizeUserId(String userId) {
        if (userId == null) {
            return null;
        }
        String trimmed = userId.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
