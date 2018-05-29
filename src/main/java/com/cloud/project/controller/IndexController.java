package com.cloud.project.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.model.Login;
import com.cloud.project.model.Register;
import com.cloud.project.model.User;
import com.cloud.project.service.UserService;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private UserService userService;

	@ModelAttribute("login")
	public Login getLoginObject() {
		return new Login();
	}
	
	@ModelAttribute("register")
	public Register getRegisterObject() {
		return new Register();
	}
	
//	@ModelAttribute("mainpage")
//	public Register getMainPageObject() {
//		return new MainPage();
//	}
	

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSubmit(@ModelAttribute Login login) {
		System.out.println(login.getEmail());
		System.out.println(login.getPassword());
		return "mainpage";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerSubmit(@ModelAttribute Register register) {
		User user = new User();
		
		user.setEmail(register.getEmailReg());
		user.setFirstName(register.getFirstName());
		user.setLastName(register.getLastName());
		user.setPassword("12345");
		
		try {
			userService.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "result";
	}
	
	public UserService getUserSerivce() {
		return userService;
	}

	public void setUserSerivce(UserService userSerivce) {
		this.userService = userSerivce;
	}

}
