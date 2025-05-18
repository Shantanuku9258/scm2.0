package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.Services.UserService;
import com.scm.entities.user;
import com.scm.helper.Helper;

@ControllerAdvice//method run for every class
public class Rootccontrolller {

      @Autowired
    private UserService  userservice;

     Logger logger = LoggerFactory.getLogger( UserController.class);
    
   @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }
        System.out.println("Added logged in user information");
        String username=Helper.getEmailOfLoggedInuser(authentication);
        logger.info("User logged in:{}",username);

        //database se data ko fetch :get user from db
              user User= userservice.getUserbyEmail(username);
             
             
                System.out.println(User.getName());
                System.out.println(User.getEmail());
                model.addAttribute("loggedInUser", User);

              
             
       

    }
    

}
