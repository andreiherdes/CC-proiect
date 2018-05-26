package com.cloud.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cloud.project.dao.UserDao;
import com.cloud.project.database.CloudSqlConnection;
import com.cloud.project.model.User;

public class UserDaoImpl implements UserDao {

	Connection conn;

	@Override
	public User getById(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		User user = new User(result.getLong(User.FLD_ID), result.getString(User.FLD_USERNAME),
				result.getString(User.FLD_PASSWORD), result.getString(User.FLD_EMAIL),
				result.getString(User.FLD_FIRST_NAME), result.getString(User.FLD_LAST_NAME));

		conn.close();

		return user;
	}

	@Override
	public List<User> getAll() throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + User.USER_TABLE);
		ResultSet result = stmt.executeQuery();

		List<User> users = new ArrayList<>();
		while (result.next()) {
			users.add(new User(result.getLong(User.FLD_ID), result.getString(User.FLD_USERNAME),
					result.getString(User.FLD_PASSWORD), result.getString(User.FLD_EMAIL),
					result.getString(User.FLD_FIRST_NAME), result.getString(User.FLD_LAST_NAME)));
		}
		conn.close();
		return users;
	}

	@Override
	public void persist(User entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + User.USER_TABLE + "(" + User.FLD_USERNAME + ","
				+ User.FLD_PASSWORD + "," + User.FLD_EMAIL + "," + User.FLD_FIRST_NAME + "," + User.FLD_LAST_NAME
				+ ") VALUES (?,?,?,?,?)");
		stmt.setString(1, entity.getUsername());
		stmt.setString(2, entity.getPassword());
		stmt.setString(3, entity.getEmail());
		stmt.setString(4, entity.getFirstName());
		stmt.setString(5, entity.getLastName());

		stmt.executeQuery();
		conn.close();

	}

	@Override
	public void deleteById(long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + User.USER_TABLE + " WHERE ID = ?");
		stmt.setLong(1, id);

		stmt.executeQuery();
		conn.close();

	}

	@Override
	public boolean isEmailValid(String email) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_EMAIL + " = ?");
		stmt.setString(1, email);

		ResultSet result = stmt.executeQuery();

		boolean isValid = result.next() ? true : false;
		conn.close();

		return isValid;
	}

	@Override
	public boolean isUsernameValid(String username) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_USERNAME + " = ?");
		stmt.setString(1, username);

		ResultSet result = stmt.executeQuery();
		boolean isValid = result.next() ? true : false;
		conn.close();

		return isValid;
	}

	@Override
	public User getByCredentials(String password, String username) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE "
				+ User.FLD_PASSWORD + " = ? AND " + User.FLD_USERNAME + " = ?");
		stmt.setString(1, password);
		stmt.setString(2, username);

		ResultSet result = stmt.executeQuery();
		User user = new User(result.getLong(User.FLD_ID), result.getString(User.FLD_USERNAME),
				result.getString(User.FLD_PASSWORD), result.getString(User.FLD_EMAIL),
				result.getString(User.FLD_FIRST_NAME), result.getString(User.FLD_LAST_NAME));

		conn.close();

		return user;
	}

}
