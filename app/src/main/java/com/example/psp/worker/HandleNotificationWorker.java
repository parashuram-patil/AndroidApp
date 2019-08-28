package com.example.psp.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.psp.constants.Constants;
import com.example.psp.room.entity.NotificationEntity;
import com.example.psp.util.Util;

public class HandleNotificationWorker extends Worker {

    private Context context;

    public HandleNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        NotificationEntity entity = Util.getNotificationEntity(getInputData());
        Class<?> clazz = Util.getPendingIntentClass(getInputData());

        Util.saveNotification(entity, context);
        Util.sendNotification(entity, context, clazz);
        Util.incrementNotificationCount();
        Log.d(Constants.TAG_HANDLE_NOTIFICATION_WORKER, "Handled notification : " + entity.getNotificationId());
        return Result.success();
    }
}
