package com.cloud.project.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.CarLicense;
import com.cloud.project.model.User;

public class DaoUtils {

	static void loadUser(ResultSet result, User user, List<CarLicense> cars) throws SQLException {
		user.setId(result.getLong(User.FLD_ID));
		user.setPassword(result.getString(User.FLD_PASSWORD));
		user.setEmail(result.getString(User.FLD_EMAIL));
		user.setFirstName(result.getString(User.FLD_FIRST_NAME));
		user.setLastName(result.getString(User.FLD_LAST_NAME));
		user.setCars(cars);

	}

	static void loadCar(ResultSet result, CarLicense car) throws SQLException {
		car.setId(result.getLong(CarLicense.FLD_CAR_ID));
		car.setLicense(result.getString(CarLicense.FLD_LICENSE));
		car.setOwnerId(result.getLong(CarLicense.FLD_FK_USER_ID));
	}
}
