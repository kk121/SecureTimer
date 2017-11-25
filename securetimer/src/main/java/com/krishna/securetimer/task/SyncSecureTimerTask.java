package com.krishna.securetimer.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.krishna.securetimer.utils.PrefManager;
import com.krishna.securetimer.utils.Utility;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by krishna on 25/11/17.
 */
public class SyncSecureTimerTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SyncSecureTimerTask";
    private PrefManager prefManager;
    private Context context;
    private static List<String> timeServerList = Arrays.asList("ntp02.oal.ul.pt", "ntp04.oal.ul.pt", "ntp.xs4all.nl");

    public SyncSecureTimerTask(Context context, String[] preferredTimeServers) {
        this.context = context.getApplicationContext();
        prefManager = new PrefManager(context);
        if (preferredTimeServers != null) {
            for (int i = 0; i < preferredTimeServers.length; i++) {
                String timeServer = preferredTimeServers[i];
                timeServerList.add(i, timeServer);
            }
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Date curDate = getDateFromNTPServer();
        if (curDate != null) {
            long curTime = curDate.getTime();
            long elapsedTime = SystemClock.elapsedRealtime();
            //update the real device boot time and sync the status
            prefManager.setRealDeviceBootTime(curTime - elapsedTime);
            prefManager.setSecureTimerSyncedStatus(true);
            Utility.cancelSecureTimerSyncJob(context);
            Log.d(TAG, "sync date: " + curDate);
        }
        return null;
    }

    private static Date getDateFromNTPServer() {
        NTPUDPClient client = new NTPUDPClient();
        client.setDefaultTimeout(5000);
        for (String host : timeServerList) {
            try {
                InetAddress hostAddr = InetAddress.getByName(host);
                Log.d(TAG, "getDateFromNTPServer > " + hostAddr.getHostName() + "/" + hostAddr.getHostAddress());
                TimeInfo info = client.getTime(hostAddr);
                return new Date(info.getMessage().getTransmitTimeStamp().getTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.close();
        return null;
    }
}
