package com.cloud.project.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.project.component.security.UserSession;
import com.cloud.project.model.CarLicense;
import com.cloud.project.service.CarLicenseService;
import com.cloud.project.service.impl.UserServiceImpl;

import java.util.*;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	public static final String ACCOUNT_SID = "ACffb55af203515c6a399d9002d4c793f5";
	public static final String AUTH_TOKEN = "1cfaa9ef61e0b95eacdc92e50cbc6f00";

	@Autowired
	private UserSession userSession;

	@Autowired
	private CarLicenseService carLicenseService;

	@RequestMapping(method = RequestMethod.GET)
	public String loadPage(Model model, HttpServletRequest request) {
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		model.addAttribute("licensePlates", userSession.getLoggedInUser().getCars());
		model.addAttribute("carLicense", new CarLicense());

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
	public RedirectView sendAccidentNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}

	@RequestMapping(value = "/sendPickupNotif", method = RequestMethod.POST)
	public RedirectView sendPickUpNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}
	
	@RequestMapping(value = "/sendParkNotif", method = RequestMethod.POST)
	public RedirectView sendParkNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}
	
	@RequestMapping(value = "/sendThiefNotif", method = RequestMethod.POST)
	public RedirectView sendThiefNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}
	
	@RequestMapping(value = "/sendWheelBlockNotif", method = RequestMethod.POST)
	public RedirectView sendWheelBlockNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}
	
	@RequestMapping(value = "/sendCarLightNotif", method = RequestMethod.POST)
	public RedirectView sendCarLightNotification(@ModelAttribute CarLicense carLicense)
			throws TwilioRestException, SQLException {
		System.out.println(carLicense.getLicense());

		List<String> phoneNumbers = new UserServiceImpl().getAllPhoneNumbersByLicenseNumber(carLicense.getLicense());
		for (int i = 0; i < phoneNumbers.size(); i++) {
			System.out.println(phoneNumbers.get(i));
		}
		// sendSms();
		return new RedirectView("/mainpage");
	}
	
	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public void sendSms(String to, String smsText) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		+40744887339
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", "+18722334233"));
		params.add(new BasicNameValuePair("Body", smsText));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);

		System.out.println(message.getSid());
	}
}
