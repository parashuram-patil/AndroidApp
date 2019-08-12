package com.example.psp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.psp.adapter.NotificationListAdapter;
import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;
import com.example.psp.room.db.DatabaseClient;
import com.example.psp.room.entity.NotificationEntity;
import com.example.psp.util.Util;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle(Constants.TITLE_NOTIFICATION);

        ListView listView = findViewById(R.id.notificationList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView titleView = (TextView) ((RelativeLayout) view).getChildAt(0);
                Util.makeTextNormal(titleView);
                TextView bodyView = (TextView) ((RelativeLayout) view).getChildAt(1);
                Util.makeTextNormal(bodyView);
                NotificationEntity item = (NotificationEntity) parent.getItemAtPosition(position);
                item.setIsRead(true);
                new UpdateNotificationTask(NotificationActivity.this, item.getNotificationId()).execute();
                Util.showOkPopUp(NotificationActivity.this, titleView.getText(), bodyView.getText(), R.drawable.notiifcation_1x);
            }
        });

        new FetchNotificationsTask(this).execute();
    }

    private static class FetchNotificationsTask extends AsyncTask<Void, Void, List<NotificationEntity>> {

        private WeakReference<Activity> activityWeakReference;

        FetchNotificationsTask(Activity activityWeakReference) {
            this.activityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        protected List<NotificationEntity> doInBackground(Void... voids) {

            return getNotifications(activityWeakReference.get());
        }

        @Override
        protected void onPostExecute(List<NotificationEntity> notifications) {
            ListView listView = activityWeakReference.get().findViewById(R.id.notificationList);
            NotificationListAdapter adapter = new NotificationListAdapter(activityWeakReference.get(), notifications);
            listView.setAdapter(adapter);
        }

        private List<NotificationEntity> getNotifications(Context context) {

            return DatabaseClient.getDatabaseClient(context).getAppDatabase().notificationDao().getAllNotifications();
        }
    }

    private static class UpdateNotificationTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activityWeakReference;
        private String notificationId;

        public UpdateNotificationTask(Activity activity, String notificationId) {
            this.activityWeakReference = new WeakReference<>(activity);
            this.notificationId = notificationId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            updateNotification(this.activityWeakReference.get(), this.notificationId);
            return null;
        }

        private void updateNotification(Context context, String notificationId) {
            DatabaseClient.getDatabaseClient(context).getAppDatabase().notificationDao().markAsRead(notificationId);
        }
    }
}
