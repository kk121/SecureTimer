package com.krishna.securetimer.utils;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.krishna.securetimer.service.SecureTimerSyncJob;

/**
 * Created by krishna on 25/11/17.
 */

public class Utility {

    public static final String EXTRA_PREFERRED_TIME_SERVERS = "preferred_time_servers";

    public static void scheduleJobToSyncSecureTimer(Context context, String[] preferredTimeServers) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(EXTRA_PREFERRED_TIME_SERVERS, preferredTimeServers);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context.getApplicationContext()));
        Job job = jobDispatcher.newJobBuilder()
                .setService(SecureTimerSyncJob.class)
                .setTag(SecureTimerSyncJob.TAG)
                .setRecurring(false)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.NOW)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(bundle)
                .build();
        jobDispatcher.mustSchedule(job);
    }

    public static void cancelSecureTimerSyncJob(Context context) {
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        jobDispatcher.cancel(SecureTimerSyncJob.TAG);
    }
}
