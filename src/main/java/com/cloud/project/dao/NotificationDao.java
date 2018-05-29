package com.cloud.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.Notification;

public interface NotificationDao {

	List<Notification> getNotificationsByUserId(Long id) throws SQLException;

	List<Notification> getNotificationsByIssuer(Long id) throws SQLException;
}
