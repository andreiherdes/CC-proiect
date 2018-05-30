package com.cloud.project.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.project.component.EmailServiceImpl;
import com.cloud.project.component.security.UserSession;
import com.cloud.project.model.Notification;
import com.cloud.project.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserSession userSession;

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loggedUser", method = RequestMethod.GET)
	public RedirectView getLoggedUsers() {
		System.out.println(userSession.getLoggedInUser());
		emailService.sendSimpleMessage(userSession.getLoggedInUser().getEmail(), "subject", "hello");
		return new RedirectView("/");
	}

	@RequestMapping(value = "/testQuery", method = RequestMethod.GET)
	public RedirectView testQuery() throws SQLException {
		System.out.println(userService.getAllPhoneNumbersByLicenseNumber("IS28ASD"));
		return new RedirectView("/");
	}

	@RequestMapping(value = "/addNotification", method = RequestMethod.GET)
	public RedirectView addNotifications() throws Exception {
		Notification entity = new Notification();
		entity.setUserId(userSession.getLoggedInUser().getId());
		entity.setAlertType("Thief");
		entity.setCarLicenseId((long) 2);
		entity.setIssuerId((long) 2);
		entity.setTimestamp(new java.sql.Date(System.currentTimeMillis()));
		// userService.processAddNotification(entity);
		return new RedirectView("/");
	}

	@RequestMapping(value = "/getNotifications", method = RequestMethod.GET)
	public RedirectView testNotifications() throws Exception {
		Notification entity = new Notification();
		entity.setAlertType("Thief");
		entity.setCarLicenseId((long) 2);
		entity.setIssuerId((long) 2);
		entity.setTimestamp(new java.sql.Date(System.currentTimeMillis()));
		// userService.processAddNotification(entity);
		return new RedirectView("/");
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public EmailServiceImpl getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailServiceImpl emailService) {
		this.emailService = emailService;
	}

}
