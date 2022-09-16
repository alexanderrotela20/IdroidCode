package com.ardev.idroid.app;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Utility class to retrieve the application context from anywhere.
 */
public class AppProvider {

    private static Context mContext;

    public static void init(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        if (mContext == null) {
            throw new IllegalStateException("init() has not been called yet in AppProvider.class ");
        }
        return mContext;
    }
}
