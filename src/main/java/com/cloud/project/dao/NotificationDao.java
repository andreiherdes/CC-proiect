package com.cloud.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.Notification;

public interface NotificationDao {

	List<Notification> getNotificationsByUserId(Long id) throws SQLException;

	List<Notification> getNotificationsByIssuer(Long id) throws SQLException;

	void persist(Notification entity) throws SQLException;

	public Notification getNotificationByIssuerIdUserIdCarId(Long issuerId, Long userId, Long carId)
			throws SQLException;
}
