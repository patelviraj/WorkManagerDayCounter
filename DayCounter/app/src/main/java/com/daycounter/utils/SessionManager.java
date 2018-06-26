package com.daycounter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.daycounter.R;


public class SessionManager {

    private Editor editor;
    private SharedPreferences pref;

    public static final String KEY_CURRENT_DAY = "current_day";
    public static final String KEY_WORKER_UUID = "worker_uuid";

    public static final String KEY_IS_COMPLETED = "is_day_completed";

    public SessionManager(Context context) {
        String PREF_NAME = context.getResources().getString(R.string.app_name);
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setStringDataByKey(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringDataByKey(String key) {
        return pref.getString(key, "");
    }

    public int getIntegerDataByKey(String key) {
        return pref.getInt(key, 0);
    }

    public boolean getBooleanDataByKey(String key) {
        return pref.getBoolean(key, false);
    }

    public void setBooleanDataByKey(String key, boolean isTrue) {
        editor.putBoolean(key, isTrue);
        editor.commit();
    }
}
