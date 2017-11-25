package com.krishna.securetimer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by krishna on 25/11/17.
 */
public class PrefManager {
    private static final String SHARED_PREF_NAME = "secure_timer_pref";
    private static final String KEY_REAL_DEVICE_BOOT_TIME = "device_boot_time";
    private static final String KEY_IS_DEVICE_BOOT_TIME_SET = "is_device_boot_time_set";
    private static final String KEY_PREFERRED_TIME_SERVERS = "preferred_time_servers";
    private SharedPreferences sharedPref;

    public PrefManager(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public long getRealDeviceBootTime() {
        return sharedPref.getLong(KEY_REAL_DEVICE_BOOT_TIME, 0);
    }

    public void setRealDeviceBootTime(long timeInMillis) {
        sharedPref.edit().putLong(KEY_REAL_DEVICE_BOOT_TIME, timeInMillis).apply();
    }

    public void setSecureTimerSyncedStatus(boolean b) {
        sharedPref.edit().putBoolean(KEY_IS_DEVICE_BOOT_TIME_SET, b).apply();
    }

    public boolean isSecureTimerSynced() {
        return sharedPref.getBoolean(KEY_IS_DEVICE_BOOT_TIME_SET, false);
    }

    public void savePreferredTimeServers(String[] timeServers) {
        if (timeServers != null) {
            Set<String> timeServerSet = new HashSet<>(Arrays.asList(timeServers));
            sharedPref.edit().putStringSet(KEY_PREFERRED_TIME_SERVERS, timeServerSet).apply();
        } else {
            sharedPref.edit().remove(KEY_PREFERRED_TIME_SERVERS).apply();
        }
    }

    public String[] getPreferredTimeServers() {
        Set<String> timeServers = sharedPref.getStringSet(KEY_PREFERRED_TIME_SERVERS, null);
        if (timeServers != null) {
            String[] timeServerArr = new String[timeServers.size()];
            return timeServers.toArray(timeServerArr);
        }
        return null;
    }
}
