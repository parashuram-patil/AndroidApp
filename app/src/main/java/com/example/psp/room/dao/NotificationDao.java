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

    @Query(" UPDATE NOTIFICATION SET IS_READ = 1 WHERE NOTIFICATION_ID = :notificationId")
    void markAsRead(String notificationId);

    @Query("SELECT * FROM NOTIFICATION WHERE NOTIFICATION_ID = :notificationId")
    NotificationEntity getNotificationById(String notificationId);

    @Query("SELECT * FROM NOTIFICATION ORDER BY CREATION_TIME DESC")
    List<NotificationEntity> getAllNotifications();

    @Query("DELETE FROM NOTIFICATION WHERE IS_READ = 1 AND CREATION_TIME < :creationTime")
    void clearNotifications(Long creationTime);

    /*@Delete
    void delete(NotificationEntity task);*/
}
