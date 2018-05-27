package com.cloud.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloud.project.dao.CarLicenseDao;
import com.cloud.project.dao.UserDao;
import com.cloud.project.database.CloudSqlConnection;
import com.cloud.project.model.CarLicense;
import com.cloud.project.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private Connection conn;
	private CarLicenseDao carDao = new CarLicenseDaoImpl();

	@Override
	public User getById(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		User user = new User();

		if (result.next()) {
			List<CarLicense> cars = carDao.getAll(result.getLong(User.FLD_ID));
			DaoUtils.loadUser(result, user, cars);
		}
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
			User user = new User();
			List<CarLicense> cars = carDao.getAll(result.getLong(User.FLD_ID));

			users.add(user);
			DaoUtils.loadUser(result, user, cars);
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

		for (CarLicense car : entity.getCars()) {
			carDao.persist(car);
		}

		stmt.executeUpdate();
		conn.close();

	}

	@Override
	public void deleteById(long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + User.USER_TABLE + " WHERE ID = ?");
		stmt.setLong(1, id);

		int deleted = stmt.executeUpdate();

		if (deleted == 0) {
			throw new SQLException("Nothing to delete");
		}
		conn.close();
	}

	@Override
	public boolean isEmailValid(String email) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_EMAIL + " = ?");
		stmt.setString(1, email);

		ResultSet result = stmt.executeQuery();

		boolean isValid = result.next() ? false : true;
		conn.close();

		return isValid;
	}

	@Override
	public boolean isUsernameValid(String username) throws SQLException {

		if (username.isEmpty()) {
			return false;
		}

		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + User.USER_TABLE + " WHERE " + User.FLD_USERNAME + " = ?");
		stmt.setString(1, username);

		ResultSet result = stmt.executeQuery();
		boolean isValid = result.next() ? false : true;
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
		User user = new User();
		if (result.next()) {
			DaoUtils.loadUser(result, user, null);
		}

		conn.close();

		return user;
	}

}
