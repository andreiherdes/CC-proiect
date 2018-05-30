package com.cloud.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.project.component.EmailServiceImpl;
import com.cloud.project.component.security.UserSession;
import com.cloud.project.model.CarLicense;
import com.cloud.project.model.Notification;
import com.cloud.project.model.User;
import com.cloud.project.service.CarLicenseService;
import com.cloud.project.service.UserService;
import com.cloud.project.utils.Messages;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	public static final String ACCOUNT_SID = "ACffb55af203515c6a399d9002d4c793f5";
	public static final String AUTH_TOKEN = "1cfaa9ef61e0b95eacdc92e50cbc6f00";

	@Autowired
	private UserSession userSession;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private CarLicenseService carLicenseService;

	@RequestMapping(method = RequestMethod.GET)
	public String loadPage(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		model.addAttribute("licensePlates", userSession.getLoggedInUser().getCars());
		model.addAttribute("carLicense", new CarLicense());
		Long loggedUserId = userSession.getLoggedInUser().getId();
		model.addAttribute("notifications", userService.getAllNotificationsForUserId(loggedUserId));

		return "mainpage";
	}

	@RequestMapping(value = "/addNewLicense", method = RequestMethod.POST)
	public RedirectView addNewLicenseSubmit(@ModelAttribute CarLicense carLicense) throws SQLException {
		CarLicense carLicenseNew = new CarLicense();
		carLicenseNew.setLicense(carLicense.getLicense());
		carLicenseNew.setOwnerId(userSession.getLoggedInUser().getId());
		userSession.getLoggedInUser().getCars().add(carLicenseNew);// adauga doar in lista

		carLicenseService.addCarLicense(carLicenseNew);

		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/deleteLicense", method = RequestMethod.POST)
	public RedirectView deleteLicenseSubmit(@ModelAttribute CarLicense carLicense) throws Exception {
		try {
			carLicenseService.deleteByLicenseNumberAndOwnerId(carLicense.getLicense(),
					userSession.getLoggedInUser().getId());
			userSession.getLoggedInUser().getCars().removeIf(car -> car.getLicense().equals(carLicense.getLicense()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public RedirectView performLogout() {
		userSession.setLoggedInUser(null);
		return new RedirectView("/");
	}

	@RequestMapping(value = "/sendAccidentNotif", method = RequestMethod.POST)
	public RedirectView sendAccidentNotification(@ModelAttribute CarLicense carLicense) throws Exception {
		System.out.println(carLicense.getLicense());

		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.ACCIDENT_NOTIFICATION, Messages.ACCIDENT_MESSAGE);

			Notification notification = buildNotification(user, issuer, "ACCIDENT");
			userService.processAddNotification(notification, carLicense.getLicense());

		}
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendPickupNotif", method = RequestMethod.POST)
	public RedirectView sendPickUpNotification(@ModelAttribute CarLicense carLicense)
			throws Exception {
		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.PICK_UP_NOTIFICATION, Messages.PICK_UP_MESSAGE);

			Notification notification = buildNotification(user, issuer, "PICK_UP");
			userService.processAddNotification(notification, carLicense.getLicense());
		}
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendParkNotif", method = RequestMethod.POST)
	public RedirectView sendParkNotification(@ModelAttribute CarLicense carLicense)
			throws Exception {
		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.PARK_NOTIFICATION, Messages.PARK_MESSAGE);

			Notification notification = buildNotification(user, issuer, "WRONG_PARKING");
			userService.processAddNotification(notification, carLicense.getLicense());
		}
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendThiefNotif", method = RequestMethod.POST)
	public RedirectView sendThiefNotification(@ModelAttribute CarLicense carLicense)
			throws Exception {
		System.out.println(carLicense.getLicense());

		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.BURGLARY_NOTIFICATION, Messages.BURGLARY_MESSAGE);

			Notification notification = buildNotification(user, issuer, "BURGLARY");
			userService.processAddNotification(notification, carLicense.getLicense());
		}
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendWheelBlockNotif", method = RequestMethod.POST)
	public RedirectView sendWheelBlockNotification(@ModelAttribute CarLicense carLicense)
			throws Exception {
		System.out.println(carLicense.getLicense());

		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.BLOCKED_WHEEL_NOTIFICATION, Messages.BLOCKED_WHEEL_MESSAGE);

			Notification notification = buildNotification(user, issuer, "BLOCKED_WHEEL");
			userService.processAddNotification(notification, carLicense.getLicense());
		}
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendCarLightNotif", method = RequestMethod.POST)
	public RedirectView sendCarLightNotification(@ModelAttribute CarLicense carLicense)
			throws Exception {
		System.out.println(carLicense.getLicense());

		List<User> affectedUsers = userService.getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		User issuer = userSession.getLoggedInUser();
		for (User user : affectedUsers) {
			processMessages(user, Messages.LIGHTS_ON_NOTIFICATION, Messages.LIGHTS_ON_MESSAGE);

			Notification notification = buildNotification(user, issuer, "LIGHTS ON");
			userService.processAddNotification(notification, carLicense.getLicense());
		}
		return new RedirectView("/mainpage");
	}

	private void sendSms(String to, String smsText) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// +40744887339
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", "+18722334233"));
		params.add(new BasicNameValuePair("Body", smsText));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);

		System.out.println(message.getSid());
	}

	private Notification buildNotification(User affectedUser, User issuer, String alertType) {
		Notification notification = new Notification();
		notification.setUserId(affectedUser.getId());
		notification.setIssuerId(issuer.getId());
		notification.setAlertType(alertType);

		return notification;
	}

	private void processMessages(User user, String notificationTitle, String message) throws TwilioRestException {
		emailService.sendSimpleMessage(user.getEmail(), notificationTitle, message);
		sendSms("+4" + user.getPhoneNumber(), notificationTitle + message);
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
