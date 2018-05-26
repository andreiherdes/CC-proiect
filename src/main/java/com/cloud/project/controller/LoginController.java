package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cloud.project.model.Login;

@Controller
public class LoginController {

	@GetMapping("/")
	public String loginForm(Model model){
		model.addAttribute("login", new Login());
		return "index";
	}
	
	@PostMapping("/")
	public String loginSubmit(@ModelAttribute Login login) {
		System.out.println(login.getEmail());
		System.out.println(login.getPassword());
		return "result";
	}
	
}
