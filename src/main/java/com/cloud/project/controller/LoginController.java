package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.model.Login;

@Controller
@RequestMapping( "/asdfas" )
public class LoginController {

	@RequestMapping( method = RequestMethod.GET )
	public String loginForm(Model model){
		model.addAttribute("login", new Login());
		return "index";
	}
	
	@RequestMapping( method = RequestMethod.POST )
	public String loginSubmit(@ModelAttribute Login login) {
		System.out.println(login.getEmail());
		System.out.println(login.getPassword());
		return "mainpage";
	}
	
}
