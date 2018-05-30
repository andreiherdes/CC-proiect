package com.cloud.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.User;

public interface UserDao {

	// TODO implement generic DAO

	User getById(Long id) throws SQLException;

	List<User> getAll() throws SQLException;

	void persist(User entity) throws SQLException;

	void deleteById(long id) throws SQLException;

	boolean isEmailValid(String email) throws SQLException;

	User getByCredentials(String password, String email) throws SQLException;

	List<User> getAllPhoneNumbersByLicenseNumber(String licenseNumber) throws SQLException;

}
