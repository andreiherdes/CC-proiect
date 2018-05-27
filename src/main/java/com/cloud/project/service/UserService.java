package com.cloud.project.service;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.model.User;

public interface UserService {

	User getById(Long id) throws SQLException;
	
	List<User> getAll() throws SQLException;

	void register(User entity) throws SQLException;

	void deleteById(long id) throws SQLException;
}
