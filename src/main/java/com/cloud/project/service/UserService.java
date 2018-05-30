package com.cloud.project.service;

import java.sql.SQLException;
import java.util.List;

import com.cloud.project.dto.NotificationDTO;
import com.cloud.project.model.Notification;
import com.cloud.project.model.User;

public interface UserService {

	User getById(Long id) throws SQLException;

	List<User> getAll() throws SQLException;

	void processRegister(User entity) throws SQLException;

	User processLogin(String email, String password) throws SQLException;

	void deleteById(long id) throws SQLException;

	void processAddNotification(Notification entity, String license) throws SQLException, Exception;

	List<User> getAllPhoneNumbersByLicenseNumber(String licenseNumber) throws SQLException;

	List<NotificationDTO> getAllNotificationsForUserId(long id) throws SQLException;
}
