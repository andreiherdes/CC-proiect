package com.cloud.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mainpage")
public class MainPageController {

	@RequestMapping(method = RequestMethod.GET)
	public String loadIndex() {
		return "mainpage";
	}
}
