package com.scm.helper;

import java.security.Principal;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInuser(org.springframework.security.core.Authentication authentication){
        //agar hmne email id password se login kiya h toh email kse nikalenge ya google se kiya h toh kse ya ya github se
       
       

        if(authentication instanceof OAuth2AuthenticationToken){
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                var ClientId =oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

                var oauth2user =(OAuth2User)authentication.getPrincipal();
                String username="";

                if(ClientId.equalsIgnoreCase("google")){
                    //sign in with google
                    System.out.println("Getting email from google");
                   username= oauth2user.getAttribute("email").toString();
                }


                else if(ClientId.equalsIgnoreCase("github")){
                    //sign in with github
                    System.out.println("sign in with github");
                    username = oauth2user.getAttribute("email")!=null?
                    oauth2user.getAttribute("email").toString():oauth2user.getAttribute("login").toString()+"@gmail.com";

                }
                return username;
               

        }
        else{
            System.out.println("Gettin data from database");
            return authentication.getName();
        }
            
        
    }

}
