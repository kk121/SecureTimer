package com.krishna.securetimer.service;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.krishna.securetimer.task.SyncSecureTimerTask;
import com.krishna.securetimer.utils.Utility;

/**
 * Created by krishna on 25/11/17.
 */
public class SecureTimerSyncJob extends JobService {
    public static final String TAG = "SecureTimerSyncJob";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob() called with: job = [" + job.getTag() + "]");
        String[] preferredTimeServers = null;
        if (job.getExtras() != null) {
            preferredTimeServers = job.getExtras().getStringArray(Utility.EXTRA_PREFERRED_TIME_SERVERS);
        }
        new SyncSecureTimerTask(getApplicationContext(), preferredTimeServers).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
