package com.cloud.project.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.project.dao.UserDao;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User getById(Long id) throws SQLException {
		return userDao.getById(id);
	}

	@Override
	public List<User> getAll() throws SQLException {
		return userDao.getAll();
	}

	@Override
	public void processRegister(User entity) throws SQLException {

		if (userDao.isEmailValid(entity.getEmail())) {
			userDao.persist(entity);
		} else {
			throw new SQLException("Invalid Email!");
		}

	}

	@Override
	public void deleteById(long id) throws SQLException {
		userDao.deleteById(id);
	}

	@Override
	public User processLogin(String email, String password) throws SQLException {
		return userDao.getByCredentials(password, email);
	}

}
