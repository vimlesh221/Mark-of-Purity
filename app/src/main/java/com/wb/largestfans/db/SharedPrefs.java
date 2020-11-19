package com.wb.largestfans.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.wb.largestfans.R;


/**
 * @author vimleshn NSeptember 06, 2017
 *         SharedPrefs.java
 * @description this class use to store chunk data use in application.
 */

public class SharedPrefs {

    //create SharedPreferences file if not present
    private static SharedPreferences getPrefs(Context context) {

        String SHARED_PREFS_FILE_NAME = context.getString(R.string.shared_pref);

        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    //Save Booleans
    public static void save(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    //Get Booleans
    @SuppressWarnings("unused")
    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    //Get Booleans if not found return a predefined default value
    public static boolean getBoolean(Context context, String key, @SuppressWarnings("SameParameterValue") boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    //Strings
    public static void save(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    // use to get string from shared preference
    public static String getString(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }

    // use to get string from shared preference . In case of no value found then puted by default value
    @SuppressWarnings("unused")
    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static int getInt(Context context, String key, @SuppressWarnings("SameParameterValue") int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }

    //Strings
    public static void saveInt(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).apply();
    }

    public static long getLong(Context context, String key, @SuppressWarnings("SameParameterValue") long defaultValue) {
        return getPrefs(context).getLong(key, defaultValue);
    }

    //Strings
    public static void saveLong(Context context, String key) {
        getPrefs(context).edit().putLong(key, (long) 0).apply();
    }

    //Strings
    @SuppressWarnings("unused")
    public static void clear(Context context) {
        getPrefs(context).edit().clear().apply();
    }

    //Strings
    public static void clearTag(Context context, String tag) {
        getPrefs(context).edit().remove(tag).apply();
    }
}