package com.example.psp.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.psp.constants.Constants;
import com.example.psp.room.db.DatabaseClient;

public class ClearNotificationsWorker extends Worker {

    private Context context;

    public ClearNotificationsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Long qualifiedTime = System.currentTimeMillis() - Constants.NOTIFICATION_CLEAN_UP_TIME;

        DatabaseClient.getDatabaseClient(context).getAppDatabase().notificationDao().clearNotifications(qualifiedTime);

        Log.d(Constants.TAG_HANDLE_NOTIFICATION_WORKER, "Notification clean up executed" );
        return Result.success();
    }
}
