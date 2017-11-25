package com.krishna.securetimer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.krishna.securetimer.utils.PrefManager;
import com.krishna.securetimer.utils.Utility;

/**
 * Created by krishna on 25/11/17.
 */
public class DeviceRebootReceiver extends BroadcastReceiver {
    private static final String TAG = "DeviceRebootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        PrefManager prefManager = new PrefManager(context.getApplicationContext());
        prefManager.setRealDeviceBootTime(System.currentTimeMillis());
        prefManager.setSecureTimerSyncedStatus(false);
        String[] preferredTimeServers = prefManager.getPreferredTimeServers();
        Utility.scheduleJobToSyncSecureTimer(context.getApplicationContext(), preferredTimeServers);
    }
}
