package com.scm.controllers;

import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.Services.UserService;
import com.scm.config.oAuthAuthenticationSuccessHandler;
import com.scm.entities.user;
import com.scm.helper.Helper;



@Controller
@RequestMapping("/user")
public class UserController {

   

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userservice;


    // Automatically add logged-in user to the model
    // @ModelAttribute
    // public void addUserToModel(Model model, Principal principal) {
    //     if (principal != null) {
    //         String email = principal.getName(); // Get logged-in user's email
    //         user loggedInUser = userservice.getUserbyEmail(email); // Fetch user details
    //         model.addAttribute("loggedInUser", loggedInUser); // Add user to model
    //     }
    // }

    // User Dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    // User Profile Page
    @RequestMapping(value = "/profile")

   public String userProfile(Model model, Authentication authentication) { 
        // Get logged-in user's email
        // String email = authentication.getName();
        // logger.info("Email: " + email);
        // user loggedInUser = userservice.getUserbyEmail(email); // Fetch user details
        // model.addAttribute("loggedInUser", loggedInUser); // Add user to model
       
 

        
        
        return "user/profile";
    }
}

    

