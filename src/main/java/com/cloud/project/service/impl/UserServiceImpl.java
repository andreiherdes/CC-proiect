package com.cloud.project.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.project.dao.CarLicenseDao;
import com.cloud.project.dao.NotificationDao;
import com.cloud.project.dao.UserDao;
import com.cloud.project.dto.NotificationDTO;
import com.cloud.project.model.CarLicense;
import com.cloud.project.model.Notification;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	CarLicenseDao carDao;

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
	public void processAddNotification(Notification entity, String license) throws SQLException, Exception {
		Notification notification = notificationDao.getNotificationByIssuerIdUserIdCarId(entity.getIssuerId(),
				entity.getUserId(), entity.getCarLicenseId());

		CarLicense carLicense = null;

		if (notification.getId() > 0) {
			int tenMinutes = 10 * 60 * 1000;
			long tenAgo = System.currentTimeMillis() - tenMinutes;
			if (notification.getTimestamp().before(new java.sql.Date(tenAgo))) {
				carLicense = carDao.getByLicenseNumber(license);
				entity.setCarLicenseId(carLicense.getId());
				notificationDao.persist(entity);
			} else {
				throw new Exception("Notification already issued!");
			}
		} else {
			carLicense = carDao.getByLicenseNumber(license);
			notificationDao.persist(entity);
		}

	}

	@Override
	public List<User> getAllPhoneNumbersByLicenseNumber(String licenseNumber) throws SQLException {
		return userDao.getAllPhoneNumbersByLicenseNumber(licenseNumber);
	}

	@Override
	public List<NotificationDTO> getAllNotificationsForUserId(long id) throws SQLException {
		List<NotificationDTO> notificationDTOs = new ArrayList<>();

		List<Notification> notifications = notificationDao.getNotificationsByUserId(id);

		for (Notification notif : notifications) {
			CarLicense car = carDao.getById(notif.getCarLicenseId());
			NotificationDTO notifDTO = new NotificationDTO();
			notifDTO.setNotification(notif);
			notifDTO.setCarLicense(car);

			notificationDTOs.add(notifDTO);
		}

		return notificationDTOs;
	}

}
