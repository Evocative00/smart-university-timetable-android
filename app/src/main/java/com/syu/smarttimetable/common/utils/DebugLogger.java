package com.syu.smarttimetable.common.utils;

public class DebugLogger {

    private static final String TAG = "SmartTimetable";

    public static void d(String message) {
        android.util.Log.d(TAG, message);
    }

    public static void e(String message) {
        android.util.Log.e(TAG, message);
    }

    public static void i(String message) {
        android.util.Log.i(TAG, message);
    }
}