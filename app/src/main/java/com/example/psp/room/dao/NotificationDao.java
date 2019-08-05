package com.example.psp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.psp.room.entity.NotificationEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insert(NotificationEntity task);

    @Update
    void update(NotificationEntity task);

    @Query("SELECT * FROM NOTIFICATION WHERE NOTIFICATION_ID = :notificationId")
    NotificationEntity getNotificationById(String notificationId);

    @Query("SELECT * FROM NOTIFICATION")
    List<NotificationEntity> getAllNotifications();

    @Query("DELETE FROM NOTIFICATION WHERE CREATION_TIME < :creationTime")
    void clearNotifications(Long creationTime);

    /*@Delete
    void delete(NotificationEntity task);*/
}
