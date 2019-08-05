package com.example.psp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.psp.room.entity.NotificationEntity;

@Dao
public interface NotificationDao {

    @Insert
    void insert(NotificationEntity task);

    @Query("SELECT DISTINCT * FROM NOTIFICATION WHERE NOTIFICATION_ID = :notificationId")
    NotificationEntity getNotificationById(String notificationId);

    @Query("DELETE FROM NOTIFICATION WHERE CREATION_TIME < :creationTime")
    void clearNotifications(Long creationTime);

    /*@Delete
    void delete(NotificationEntity task);

    @Update
    void update(NotificationEntity task);*/
}
