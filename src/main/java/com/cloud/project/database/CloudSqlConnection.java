package com.cloud.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cloud.project.utils.DatabaseCredentials;

public class CloudSqlConnection {

	public final static CloudSqlConnection INSTANCE = new CloudSqlConnection();

	private final static String SQL_URL = "jdbc:mysql://google/CLOUDCOMPUTING" + "?cloudSqlInstance="
			+ DatabaseCredentials.INSTANCE_CONNECTION_NAME
			+ "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=" + DatabaseCredentials.USERNAME
			+ "&password=" + DatabaseCredentials.PASSWORD + "&useSSL=false";

	private CloudSqlConnection() {
		
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(SQL_URL);
	}

}
