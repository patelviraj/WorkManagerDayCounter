package com.daycounter.utils;

import android.util.Log;

import com.daycounter.BuildConfig;


public class Logger {
    private static String TAG = BuildConfig.APPLICATION_ID;

    public static void e(String Msg) {
        LogIt(Log.ERROR, TAG, Msg);
    }

    public static void e(String Tag, String Msg) {
        LogIt(Log.ERROR, Tag, Msg);
    }

    public static void i(String Msg) {
        LogIt(Log.INFO, TAG, Msg);
    }

    public static void i(String Tag, String Msg) {
        LogIt(Log.INFO, Tag, Msg);
    }

    public static void d(String Msg) {
        LogIt(Log.DEBUG, TAG, Msg);
    }

    public static void d(String Tag, String Msg) {
        LogIt(Log.DEBUG, Tag, Msg);
    }

    public static void v(String Msg) {
        LogIt(Log.VERBOSE, TAG, Msg);
    }

    public static void v(String Tag, String Msg) {
        LogIt(Log.VERBOSE, Tag, Msg);
    }

    public static void w(String Msg) {
        LogIt(Log.WARN, TAG, Msg);
    }

    public static void w(String Tag, String Msg) {
        LogIt(Log.WARN, Tag, Msg);
    }

    private static void LogIt(int LEVEL, String Tag, String Message) {
        if (BuildConfig.DEBUG) Log.println(LEVEL, Tag != null ? Tag : TAG, Message);
    }
}
