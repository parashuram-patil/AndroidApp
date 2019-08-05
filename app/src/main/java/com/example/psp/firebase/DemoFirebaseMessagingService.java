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

        /*Scheduling Worker to handle notification*/
        Data workerData = Util.getNotificationWorkerData(
                remoteMessage.getMessageId(),
                title,
                body,
                null
        );

        Util.scheduleNotificationJob(workerData);
    }

    @Override
    public void onNewToken(String token) {
        Util.setFcmToken(token);
    }
}