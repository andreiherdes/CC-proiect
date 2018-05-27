package com.cloud.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cloud.project.dao.CarLicenseDao;
import com.cloud.project.database.CloudSqlConnection;
import com.cloud.project.model.CarLicense;

public class CarLicenseDaoImpl implements CarLicenseDao {

	private Connection conn;

	@Override
	public CarLicense getById(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + CarLicense.CAR_LICENSE_TABLE + " WHERE " + CarLicense.FLD_CAR_ID + " = ?");
		stmt.setLong(1, id);

		ResultSet result = stmt.executeQuery();

		CarLicense car = new CarLicense();

		if (result.next()) {
			DaoUtils.loadCar(result, car);
		}
		conn.close();

		return car;
	}

	@Override
	public List<CarLicense> getAll(Long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();
		StringBuilder query = new StringBuilder("SELECT * FROM " + CarLicense.CAR_LICENSE_TABLE);
		PreparedStatement stmt = null;
		if (id != null) {
			query.append(" WHERE " + CarLicense.FLD_FK_USER_ID + " = ?");
			stmt = conn.prepareStatement(query.toString());
			stmt.setLong(1, id);
		} else {
			stmt = conn.prepareStatement(query.toString());
		}

		ResultSet result = stmt.executeQuery();
		List<CarLicense> cars = new ArrayList<>();

		while (result.next()) {
			CarLicense car = new CarLicense();
			cars.add(car);
			DaoUtils.loadCar(result, car);
		}

		conn.close();
		return cars;
	}

	@Override
	public void persist(CarLicense entity) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + CarLicense.CAR_LICENSE_TABLE + "("
				+ CarLicense.FLD_LICENSE + "," + CarLicense.FLD_FK_USER_ID + ") VALUES (?,?)");
		stmt.setString(1, entity.getLicense());
		stmt.setLong(2, entity.getOwnerId());

		stmt.executeQuery();
		conn.close();
	}

	@Override
	public void deleteById(long id) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + CarLicense.CAR_LICENSE_TABLE + " WHERE ID = ?");
		stmt.setLong(1, id);

		int deleted = stmt.executeUpdate();

		if (deleted == 0) {
			throw new SQLException("Nothing to delete");
		}
		conn.close();

	}

	@Override
	public boolean isLicenseNumberValid(String license) throws SQLException {
		conn = CloudSqlConnection.INSTANCE.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM " + CarLicense.CAR_LICENSE_TABLE+ " WHERE " + CarLicense.FLD_LICENSE + " = ?");
		stmt.setString(1, license);

		ResultSet result = stmt.executeQuery();
		boolean isValid = result.next() ? false : true;
		conn.close();

		return isValid;
	}
}
