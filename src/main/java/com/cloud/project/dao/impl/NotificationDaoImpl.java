package com.cloud.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloud.project.dao.NotificationDao;
import com.cloud.project.database.CloudSqlConnection;
import com.cloud.project.model.Notification;

@Repository
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

	@Override
	public Notification getNotificationByIssuerIdUserIdCarId(Long issuerId, Long userId, Long carId)
			throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + Notification.NOTIFICATION_TABLE + " WHERE "
				+ Notification.FLD_FK_ISSUER_ID + " = ? AND " + Notification.FLD_FK_USER_ID + " = ? AND "
				+ Notification.FLD_FK_CAR_ID + " = ?");
		stmt.setLong(1, issuerId);
		stmt.setLong(2, userId);
		stmt.setLong(3, carId);

		ResultSet result = stmt.executeQuery();
		
		Notification notification = new Notification();
		if (result.next()) {
			DaoUtils.loadNotification(result, notification);
		}
		conn.close();

		return notification;
	}

	@Override
	public void persist(Notification entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + Notification.NOTIFICATION_TABLE + "("
				+ Notification.FLD_FK_USER_ID + "," + Notification.FLD_FK_ISSUER_ID + "," + Notification.FLD_FK_CAR_ID
				+ "," + Notification.FLD_ALERT_TYPE + "," + Notification.FLD_TIMESTAMP + ") VALUES (?,?,?,?,?)");
		stmt.setLong(1, entity.getUserId());
		stmt.setLong(2, entity.getIssuerId());
		stmt.setLong(3, entity.getCarLicenseId());
		stmt.setString(4, entity.getAlertType());
		stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));

		stmt.executeUpdate();
		conn.close();

	}

}
