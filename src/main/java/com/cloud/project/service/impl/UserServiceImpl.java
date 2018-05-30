package com.cloud.project.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.project.dao.NotificationDao;
import com.cloud.project.dao.UserDao;
import com.cloud.project.model.Notification;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private NotificationDao notificationDao;

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

	@Override
	public void processAddNotification(Notification entity) throws SQLException, Exception {
		Notification notification = notificationDao.getNotificationByIssuerIdUserIdCarId(entity.getIssuerId(),
				entity.getUserId(), entity.getCarLicenseId());
		if (notification.getId() > 0) {
			int tenMinutes = 10 * 60 * 1000;
			long tenAgo = System.currentTimeMillis() - tenMinutes;
			if (notification.getTimestamp().before(new java.sql.Date(tenAgo))) {
				notificationDao.persist(entity);
			} else {
				throw new Exception("Notification already issued!");
			}
		} else {
			notificationDao.persist(entity);
		}

	}

	@Override
	public List<String> getAllPhoneNumbersByLicenseNumber(String licenseNumber) throws SQLException {
		return userDao.getAllPhoneNumbersByLicenseNumber(licenseNumber);
	}

}
