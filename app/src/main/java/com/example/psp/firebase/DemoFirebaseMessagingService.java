package com.example.psp.firebase;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.psp.R;
import com.example.psp.constants.Constants;
import com.example.psp.room.entity.NotificationEntity;
import com.example.psp.util.Util;
import com.example.psp.worker.HandleNotificationWorker;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class DemoFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> messageData = remoteMessage.getData();
        String title = "Notification Error";
        String body = "Unexpected notification received";
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null) {
            title = notification.getTitle();
            body = remoteMessage.getNotification().getBody();
        } else if (messageData != null) {
            title = messageData.get(Constants.KEY_TITLE);
            body = messageData.get(Constants.KEY_BODY);
        }

        /*Schedulig Worker to handle notification*/
        NotificationEntity entity = getNotificationEntity(remoteMessage, title, body);

        Data workerData = getNotificationWorkerData(
                remoteMessage.getMessageId(),
                title,
                body,
                null
        );

        scheduleNotificationJob(workerData);


        //sendNotification(title, body);
    }

    private void scheduleNotificationJob(Data data) {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(HandleNotificationWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private NotificationEntity getNotificationEntity(RemoteMessage remoteMessage, String title, String body) {
        NotificationEntity entity = new NotificationEntity();
        entity.setNotificationId(remoteMessage.getMessageId());
        entity.setNotificationTitle(title);
        entity.setNotificationBody(body);
        entity.setIsRead(false);
        entity.setCreationTime(System.currentTimeMillis());
        return entity;
    }

    private Data getNotificationWorkerData(String id, String title, String body, @Nullable String pendingActivityClassName) {

        return new Data.Builder()
                .putString(NotificationEntity.NOTIFICATION_ID, id)
                .putString(NotificationEntity.NOTIFICATION_TITEL, title)
                .putString(NotificationEntity.NOTIFICATION_BODY, body)
                .putBoolean(NotificationEntity.IS_READ, false)
                .putLong(NotificationEntity.CREATION_TIME, System.currentTimeMillis())
                .putString(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME, pendingActivityClassName)
                .build();
    }

    private void scheduleNotificationJob(NotificationEntity entity, Context context, @Nullable Class<?> clazz) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(Constants.KEY_NOTIFICATION_ENTITY, entity);
        dataMap.put(Constants.KEY_CONTEXT, context);
        dataMap.put(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME, clazz);

        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(HandleNotificationWorker.class)
                .setInputData(new Data.Builder()
                        .putAll(dataMap)
                        .build())
                .build();

        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void sendNotification(String title, String messageBody) {
        /*Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_image)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //.setContentIntent(pendingIntent)
        //.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Util.setFcmToken(token);
    }
}