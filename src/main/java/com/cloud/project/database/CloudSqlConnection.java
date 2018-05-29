package com.cloud.project.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CloudSqlConnection {

	public final static CloudSqlConnection INSTANCE = new CloudSqlConnection();
	
	private DatabaseCredentials dbCredentials;

	private CloudSqlConnection() {
		try {
			dbCredentials = new DatabaseCredentials();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbCredentials.getSqlUrl());
	}

}
