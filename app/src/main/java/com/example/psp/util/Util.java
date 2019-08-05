package com.example.psp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.psp.R;
import com.example.psp.constants.Constants;
import com.example.psp.room.db.DatabaseClient;
import com.example.psp.room.entity.NotificationEntity;
import com.example.psp.worker.HandleNotificationWorker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

public class Util {

    public static void openNextFragment(ViewPager vp) {
        vp.setCurrentItem(vp.getCurrentItem() + 1, true);
    }

    public static void openActivityFromFragmentActivity(FragmentActivity activity, Class<?> className, Boolean doFinish) {
        if (doFinish)
            activity.finish();
        Intent intent = new Intent(activity, className);
        activity.startActivity(intent);
    }

    public static void createNotificationChannel(Context context) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, importance);
        channel.setDescription(Constants.NOTIFICATION_CHANNEL_DESCRIPTION);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public static void setFcmToken(String token) {
        Constants.FCM_TOKEN = token;
    }

    public static void setFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(Constants.TAG_FCM, "getInstanceId failed", task.getException());
                        return;
                    }

                    String token = task.getResult().getToken();
                    Constants.FCM_TOKEN = token;
                    Log.d("Token ---> " + Constants.TAG_FCM, token);
                });
    }

    public static void scheduleNotificationJob(Data data) {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(HandleNotificationWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance().beginWith(work).enqueue();
    }

    public static NotificationEntity getNotificationEntity(RemoteMessage remoteMessage, String title, String body) {
        NotificationEntity entity = new NotificationEntity();
        entity.setNotificationId(remoteMessage.getMessageId());
        entity.setNotificationTitle(title);
        entity.setNotificationBody(body);
        entity.setIsRead(false);
        entity.setCreationTime(System.currentTimeMillis());
        return entity;
    }

    public static Data getNotificationWorkerData(String id, String title, String body, @Nullable String pendingActivityClassName) {

        return new Data.Builder()
                .putString(NotificationEntity.NOTIFICATION_ID, id)
                .putString(NotificationEntity.NOTIFICATION_TITEL, title)
                .putString(NotificationEntity.NOTIFICATION_BODY, body)
                .putBoolean(NotificationEntity.IS_READ, false)
                .putLong(NotificationEntity.CREATION_TIME, System.currentTimeMillis())
                .putString(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME, pendingActivityClassName)
                .build();
    }

    public static Class<?> getPendingIntentClass(Data data) {
        Class<?> claszz = null;
        String className = data.getString(Constants.KEY_PENDING_ACTIVITY_CLASS_NAME);
        try {
            claszz = Class.forName(className);
        } catch (ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }

        return claszz;
    }

    public static NotificationEntity getNotificationEntity(Data data) {
        NotificationEntity entity = new NotificationEntity();
        entity.setNotificationId(data.getString(NotificationEntity.NOTIFICATION_ID));
        entity.setNotificationTitle(data.getString(NotificationEntity.NOTIFICATION_TITEL));
        entity.setNotificationBody(data.getString(NotificationEntity.NOTIFICATION_BODY));
        entity.setIsRead(data.getBoolean(NotificationEntity.IS_READ, false));
        entity.setCreationTime(data.getLong(NotificationEntity.CREATION_TIME, System.currentTimeMillis()));

        return entity;
    }

    public static void saveNotification(NotificationEntity entity, Context context) {
        DatabaseClient.getDatabaseClient(context).getAppDatabase().notificationDao().insert(entity);
    }

    public static void sendNotification(NotificationEntity entity, Context context, Class<?> clazz) {

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
