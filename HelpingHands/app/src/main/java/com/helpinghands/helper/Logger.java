package com.helpinghands.helper;

import android.util.Log;

public class Logger {

    private  static  String TAG = "TAG";

    public static void show(String mess) {
        Log.e(TAG, mess);
    }
}
