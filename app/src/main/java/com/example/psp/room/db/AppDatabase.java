package com.example.psp.room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.psp.room.dao.NotificationDao;
import com.example.psp.room.entity.NotificationEntity;

@Database(entities = {NotificationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();
}
