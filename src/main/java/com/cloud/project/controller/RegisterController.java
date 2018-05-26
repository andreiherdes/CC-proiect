package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cloud.project.model.Register;

@Controller
public class RegisterController {
	
	@GetMapping("/register")
	public String registerForm(Model model){
		model.addAttribute("register", new Register());
		return "index";
	}
	
	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute Register register) {
		System.out.println(register.getFirstName());
		System.out.println(register.getLastName());
//		System.out.println(register.getBirthDate());
//		System.out.println(register.getEmail());
//		System.out.println(register.getPassword());
		return "result";
	}

}
