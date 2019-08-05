package com.example.psp.room.db;

import android.content.Context;

import androidx.room.Room;

import com.example.psp.constants.Constants;

public class DatabaseClient {

    private Context context;
    private AppDatabase appDatabase;
    private static DatabaseClient dbClient;

    private DatabaseClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, Constants.DB_FILE_NAME).build();
    }

    public static DatabaseClient getDatabaseClient(Context context) {
        if (dbClient == null) {
            synchronized (DatabaseClient.class) {
                if (dbClient == null) {
                    dbClient = new DatabaseClient(context);
                }
            }
        }

        return dbClient;
    }

    public AppDatabase getAppDatabase() {

        return appDatabase;
    }
}
