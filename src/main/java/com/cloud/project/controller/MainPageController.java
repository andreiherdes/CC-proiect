package com.cloud.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cloud.project.component.security.UserSession;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@Autowired
	private UserSession userSession;

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex(Model model, HttpServletRequest request) {
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
		return "mainpage";
	}

	@RequestMapping(value = "/test",  method = RequestMethod.POST)
	public void registerSubmit() {
		System.out.println("Test");
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
