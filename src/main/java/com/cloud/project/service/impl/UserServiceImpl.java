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
	public void register(User entity) throws SQLException {
		boolean isEmailValid = userDao.isEmailValid(entity.getEmail());
		boolean isUsernameValid = userDao.isUsernameValid(entity.getUsername());

		if (isEmailValid && isUsernameValid) {
			userDao.persist(entity);
		} else {
			if (!isEmailValid) {
				throw new SQLException("Invalid Email!");
			}
			throw new SQLException("Invalid username!");
		}

	}

	@Override
	public void deleteById(long id) throws SQLException {
		userDao.deleteById(id);
	}

}
