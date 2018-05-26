package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.model.Login;
import com.cloud.project.model.Register;

@Controller
@RequestMapping("/index")
public class IndexController {

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
	public String loginSubmit(@ModelAttribute Login login) {
		System.out.println(login.getEmail());
		System.out.println(login.getPassword());
		return "result";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerSubmit(@ModelAttribute Register register) {
		System.out.println(register.getFirstName());
		System.out.println(register.getLastName());
		// System.out.println(register.getBirthDate());
		// System.out.println(register.getEmail());
		// System.out.println(register.getPassword());
		return "result";
	}

}
