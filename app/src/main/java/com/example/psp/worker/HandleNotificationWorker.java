package com.example.psp.worker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.psp.R;
import com.example.psp.constants.Constants;
import com.example.psp.room.db.DatabaseClient;
import com.example.psp.room.entity.NotificationEntity;

public class HandleNotificationWorker extends Worker {

    private Context context;

    public HandleNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        /*Map<String, Object> inputDataMap = getInputData().getKeyValueMap();
        NotificationEntity entity = (NotificationEntity) inputDataMap.get(Constants.KEY_NOTIFICATION_ENTITY);
        Class<?> clazz = (Class<?>) inputDataMap.get(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME);*/

        NotificationEntity entity = getNotificationEntity(getInputData());
        Class<?> clazz = getPendingIntentClass(getInputData());

        saveNotification(entity, context);
        sendNotification(entity, context, clazz);

        Log.d(Constants.TAG_NOTIICATION_WORKER, "Handled notification : " + entity.getNotificationId());
        return Result.success();
    }

    private Class<?> getPendingIntentClass(Data data) {
        Class<?> claszz = null;
        String className = data.getString(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME);
        try {
            claszz = Class.forName(className);
        } catch (ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }

        return claszz;
    }

    private NotificationEntity getNotificationEntity(Data data) {
        NotificationEntity entity = new NotificationEntity();
        entity.setNotificationId(data.getString(NotificationEntity.NOTIFICATION_ID));
        entity.setNotificationTitle(data.getString(NotificationEntity.NOTIFICATION_TITEL));
        entity.setNotificationBody(data.getString(NotificationEntity.NOTIFICATION_BODY));
        entity.setIsRead(data.getBoolean(NotificationEntity.IS_READ, false));
        entity.setCreationTime(data.getLong(NotificationEntity.CREATION_TIME, System.currentTimeMillis()));

        return entity;
    }

    private void saveNotification(NotificationEntity entity, Context context) {
        DatabaseClient.getDatabaseClient(context).getAppDatabase().notificationDao().insert(entity);
    }

    private void sendNotification(NotificationEntity entity, Context context, Class<?> clazz) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_image)
                .setContentTitle(entity.getNotificationTitle())
                .setContentText(entity.getNotificationBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (clazz != null) {
            Intent intent = new Intent(context, clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(entity.getNotificationId().hashCode(), builder.build());
    }
}
