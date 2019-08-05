package com.example.psp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.psp.adapter.NotificationListAdapter;
import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;
import com.example.psp.room.db.DatabaseClient;
import com.example.psp.room.entity.NotificationEntity;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle(Constants.TITLE_NOTIFICATION);

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

}
