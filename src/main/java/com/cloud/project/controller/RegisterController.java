package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.model.Register;

@Controller
@RequestMapping( "/asdfasa" )
public class RegisterController {
	
	@RequestMapping( method = RequestMethod.GET )
	public String registerForm(Model model){
		model.addAttribute("register", new Register());
		return "index";
	}
	
	@RequestMapping( method = RequestMethod.POST )
	public String registerSubmit(@ModelAttribute Register register) {
		System.out.println(register.getFirstName());
		System.out.println(register.getLastName());
//		System.out.println(register.getBirthDate());
//		System.out.println(register.getEmail());
//		System.out.println(register.getPassword());
		return "result";
	}

}
