package com.cloud.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.project.component.security.UserSession;

@Controller
public class UserController {

	@Autowired
    UserSession userSession;
 
    @RequestMapping(value = "/loggedUser", method = RequestMethod.GET)
    public String getLoggedUsers() {
    	System.out.println(userSession.getLoggedInUser());
        return "index";
    }
}
