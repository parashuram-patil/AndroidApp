package com.example.psp.firebase;

import androidx.work.Data;

import com.example.psp.constants.Constants;
import com.example.psp.util.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class DemoFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> messageData = remoteMessage.getData();
        String title = "Notification Error";
        String body = "Unexpected notification received";
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String from = remoteMessage.getFrom();

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
        Util.setAppInstanceId(token);
    }
}