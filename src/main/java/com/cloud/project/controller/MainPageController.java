package com.cloud.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cloud.project.component.security.UserSession;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex(@ModelAttribute UserSession userSession, HttpServletRequest request) {
		System.out.println(userSession.isUserLoggedIn());
		return "mainpage";
	}
}
