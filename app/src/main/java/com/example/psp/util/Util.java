package com.example.psp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.psp.constants.Constants;
import com.google.firebase.iid.FirebaseInstanceId;

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
}
