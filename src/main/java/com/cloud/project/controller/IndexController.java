package com.cloud.project.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.component.security.UserSession;
import com.cloud.project.model.Login;
import com.cloud.project.model.Register;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Controller
@RequestMapping("/index")
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
	public String loginSubmit(@ModelAttribute Login login, HttpServletRequest request) {
		try {
			User user = userService.processLogin(login.getEmail(), login.getPassword());
			if (user.getId() > 0) {
				getUserSession().setLoggedInUser(user);
				System.out.println("Logged in");
				return "mainpage";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "result";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerSubmit(@ModelAttribute Register register) {
		User user = new User();

		user.setEmail(register.getEmailReg());
		user.setFirstName(register.getFirstName());
		user.setLastName(register.getLastName());
		user.setPassword(register.getPasswordReg());

		try {
			userService.processRegister(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "result";
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
