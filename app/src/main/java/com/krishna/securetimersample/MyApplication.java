package com.krishna.securetimersample;

import android.app.Application;

import com.krishna.securetimer.SecureTimer;

/**
 * Created by krishna on 25/11/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize secureTimer
        SecureTimer.with(getApplicationContext()).initialize();
    }
}
