package com.example.psp.firebase;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.psp.R;
import com.example.psp.constants.Constants;
import com.example.psp.util.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class DemoFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = "Notification Error";
        String body = "Unexpected notification received";
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if(notification != null) {
            title = notification.getTitle();
            body = remoteMessage.getNotification().getBody();
        }
        else if(data != null) {
            title = data.get(Constants.KEY_TITLE);
            body = data.get(Constants.KEY_BODY);
        }

        sendNotification(title, body);
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