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

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private CarLicenseService carLicenseService;

	@RequestMapping(method = RequestMethod.GET)
	public String loadPage(Model model, HttpServletRequest request) {
		System.out.println(userSession.isUserLoggedIn());
		System.out.println(userSession.getLoggedInUser().getFirstName());
		model.addAttribute("sessionUserName", userSession.getLoggedInUser().getFirstName());
		// System.out.println(userSession.getLoggedInUser().getCars());
		for (int i = 0; i < userSession.getLoggedInUser().getCars().size(); i++) {
			System.out.println(userSession.getLoggedInUser().getCars().get(i));
		}
		model.addAttribute("licensePlates", userSession.getLoggedInUser().getCars());

		for (int i = 0; i < userSession.getLoggedInUser().getNotifications().size(); i++) {
			System.out.println(userSession.getLoggedInUser().getNotifications().get(i));
		}
		model.addAttribute("carLicense", new CarLicense());
//		model.addAttribute("carLicense2", new CarLicense());
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
	public RedirectView deleteLicenseSubmit(@ModelAttribute CarLicense carLicense) {
		System.out.println(carLicense.getLicense());
		return new RedirectView("/mainpage");
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
