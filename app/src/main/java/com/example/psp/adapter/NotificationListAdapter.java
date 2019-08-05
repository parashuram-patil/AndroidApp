package com.example.psp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.psp.R;
import com.example.psp.room.entity.NotificationEntity;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends BaseAdapter {

    Context context;
    List<NotificationEntity> items;

    public NotificationListAdapter(Context context, List<NotificationEntity> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notification_item, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.body);

        titleView.setText(items.get(position).getNotificationTitle());
        subtitleView.setText(items.get(position).getNotificationBody());

        return view;
    }
}
