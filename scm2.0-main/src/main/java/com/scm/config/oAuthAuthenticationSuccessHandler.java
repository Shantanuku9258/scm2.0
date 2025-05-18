package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Repositories.UserRepo;
import com.scm.entities.Providers;
import com.scm.entities.user;
import com.scm.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class oAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
   
    Logger logger = LoggerFactory.getLogger( oAuthAuthenticationSuccessHandler.class);
    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                logger.info( "oAuthAuthenticationSuccessHandler");


                //identify the provider

                var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                String authorizedClientregistrationId =oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientregistrationId);

                var oauthuser=(DefaultOAuth2User) authentication.getPrincipal();

                oauthuser.getAttributes().forEach((key,value)->{
                    logger.info(key +" : "+value);
                });
                user User =new user();
                User.setUserId(UUID.randomUUID().toString());
                User.setRoleList(List.of(AppConstants.ROLE_USER));
                User.setEmailverified(true);
                User.setEnabled(true);
                
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode("password");
                User.setPassword(encodedPassword);
               

                if(authorizedClientregistrationId.equalsIgnoreCase("google")){
                //google
                //google attribute
                User.setEmail(oauthuser.getAttribute("email").toString());
                User.setProfilePic(oauthuser.getAttribute("picture").toString());
                User.setName(oauthuser.getAttribute("name").toString());
                User.setProviderUserId(oauthuser.getName());
                User.setProvider(Providers.GOOGLE);
                User.setAbout("This account login by Google");

                }
                 

               
                else if(authorizedClientregistrationId.equalsIgnoreCase("github")){
                    //github
                //github attribute
                    String email = oauthuser.getAttribute("email")!=null?
                    oauthuser.getAttribute("email").toString():oauthuser.getAttribute("login").toString()+"@gmail.com";
                    String picture = oauthuser.getAttribute("avatar_url").toString();
                    String name = oauthuser.getAttribute("login").toString();

                    String providerUserId = oauthuser.getName();

                    User.setEmail(email);
                    User.setName(name);
                    User.setProfilePic(picture);
                    User.setProviderUserId(providerUserId);
                    User.setProvider(Providers.GITHUB);
                    User.setAbout("This account login by GitHub");
                    

                }


                

                else if(authorizedClientregistrationId.equalsIgnoreCase("facebook")){

                }
                //facebook
                //facebook attribute
                else{
                    logger.info("OAuthSuccessfulhandler:Unknown provider");

                }

//save the user

                /*
                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

               
                logger.info(user.getName());

                user.getAttributes().forEach((key,value)->{

                    logger.info("{} =>{}",key ,value);
                });
                logger.info(user.getAuthorities().toString());

                //data datbase save
                String email= user.getAttribute("email").toString();
                String name= user.getAttribute("name").toString();
                String picture= user.getAttribute("picture").toString();

                //create user and save in dataabase
                 user user1= new user();
                 user1.setEmail(email);
                 user1.setName(name);
                 user1.setProfilePic(picture);
                 user1.setPassword("password");
                 user1.setUserId(UUID.randomUUID().toString());
                 user1.setEnabled(true);
                 user1.setEmailverified(true);
                 user1.setProviderUserId(user.getName());
                 user1.setRoleList(List.of(AppConstants.ROLE_USER));
                 user1.setAbout("This account  is created using google...");

               user user2=  userRepo.findByEmail(email).orElse(null);
               if(user2==null){
                userRepo.save(user1);
                logger.info("Usser Saved"+email);
               }
                */
                user user2=  userRepo.findByEmail(User.getEmail()).orElse(null);
                if(user2==null)
                 userRepo.save(User);

                 System.out.println("user saved:" + User.getEmail());
                 


                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
       
            

}
}
