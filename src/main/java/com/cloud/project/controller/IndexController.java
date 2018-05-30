package com.cloud.project.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.cloud.project.component.security.UserSession;
import com.cloud.project.model.Login;
import com.cloud.project.model.Register;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserSession userSession;

	@ModelAttribute("login")
	public Login getLoginObject() {
		return new Login();
	}

	@ModelAttribute("register")
	public Register getRegisterObject() {
		return new Register();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RedirectView loginSubmit(RedirectAttributes attributes, @ModelAttribute Login login,
			HttpServletRequest request) {
		try {
			User user = userService.processLogin(login.getEmail(), login.getPassword());
			if (user.getId() > 0) {
				getUserSession().setLoggedInUser(user);
				System.out.println(getUserSession().isUserLoggedIn());
				System.out.println("Logged in");
				// attributes.addAttribute(user.getFirstName());
				return new RedirectView("mainpage");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new RedirectView("/");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public RedirectView registerSubmit(RedirectAttributes attributes, @ModelAttribute Register register) {
		User user = new User();

		user.setEmail(register.getEmailReg());
		user.setFirstName(register.getFirstName());
		user.setLastName(register.getLastName());
		user.setPassword(register.getPasswordReg());
		user.setPhoneNumber(register.getPhoneNumberReg());

		try {
			userService.processRegister(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new RedirectView("/");
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
