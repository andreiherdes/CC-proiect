package com.cloud.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cloud.project.dao.NotificationDao;
import com.cloud.project.database.CloudSqlConnection;
import com.cloud.project.model.Notification;

public class NotificationDaoImpl implements NotificationDao {

	private Connection conn;

	@Override
	public List<Notification> getNotificationsByUserId(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + Notification.NOTIFICATION_TABLE + " WHERE " + Notification.FLD_FK_USER_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		List<Notification> notifications = new ArrayList<>();

		while (result.next()) {
			Notification notification = new Notification();
			notifications.add(notification);
			DaoUtils.loadNotification(result, notification);
		}
		conn.close();

		return notifications;
	}

	@Override
	public List<Notification> getNotificationsByIssuer(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + Notification.NOTIFICATION_TABLE + " WHERE "
				+ Notification.FLD_FK_ISSUER_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		List<Notification> notifications = new ArrayList<>();

		while (result.next()) {
			Notification notification = new Notification();
			notifications.add(notification);
			DaoUtils.loadNotification(result, notification);
		}
		conn.close();

		return notifications;
	}

}
