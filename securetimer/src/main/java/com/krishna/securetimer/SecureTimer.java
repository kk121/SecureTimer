package com.krishna.securetimer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.krishna.securetimer.task.SyncSecureTimerTask;
import com.krishna.securetimer.utils.PrefManager;
import com.krishna.securetimer.utils.Utility;

import java.util.Date;

/**
 * Created by krishna on 25/11/17.
 */
public class SecureTimer {
    private static final String TAG = "SecureTimer";
    private Context context;
    private static SecureTimer sInstance;
    private String[] preferredTimeServers;
    private PrefManager prefManager;

    private SecureTimer() {
    }

    private SecureTimer(Context context) {
        this.context = context.getApplicationContext();
        prefManager = new PrefManager(this.context);
    }

    public static synchronized SecureTimer with(Context context) {
        if (sInstance == null)
            sInstance = new SecureTimer(context);
        return sInstance;
    }

    public void initialize() {
        if (isNetworkSyncRequired()) {
            if (!prefManager.isSecureTimerSynced()) {
                // fall back to system time
                long curTime = System.currentTimeMillis();
                long elapsedTime = SystemClock.elapsedRealtime();
                prefManager.setRealDeviceBootTime(curTime - elapsedTime);
            }
            new SyncSecureTimerTask(context, preferredTimeServers).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            Utility.scheduleJobToSyncSecureTimer(context, preferredTimeServers);
        }
    }

    public void initialize(String... preferredTimeServers) {
        this.preferredTimeServers = preferredTimeServers;
        prefManager.savePreferredTimeServers(preferredTimeServers);
        initialize();
    }

    public long getCurrentTimeInMillis() {
        initialize();
        long realDeviceBootTime = prefManager.getRealDeviceBootTime();
        long curTime = realDeviceBootTime + SystemClock.elapsedRealtime();
        Date date = new Date(curTime);
        Log.d(TAG, "getCurrentTimeInMillis: " + date.toString());
        return curTime;
    }

    public Date getCurrentDate() {
        long currentTimeInMillis = getCurrentTimeInMillis();
        return new Date(currentTimeInMillis);
    }

    private boolean isNetworkSyncRequired() {
        boolean shouldSync = false;
        long realDeviceBootTime = prefManager.getRealDeviceBootTime();
        long secureCurTime = realDeviceBootTime + SystemClock.elapsedRealtime();
        long systemCurTime = System.currentTimeMillis();
        // if there is mismatch between system time and secure time, then sync the secureTimer with network
        if (Math.abs(systemCurTime - secureCurTime) > 60 * 1000) {
            Log.d(TAG, "isNetworkSyncRequired: " + systemCurTime + " : " + secureCurTime);
            shouldSync = true;
        }
        return !prefManager.isSecureTimerSynced() || shouldSync;
    }
}
