package com.scm.config;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.scm.Services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    //user crete and login using java code with in memory service
   
    // @Bean
    // public UserDetailsService userDetailsService(){
    //   //  this user is from org.springsecurity.
    //  UserDetails user1 = User
    //    .withDefaultPasswordEncoder()
    //     .username("admin123")
    //     .password("admin123")
    //     .roles("ADMIN","USER")
    //     .build();

    //     UserDetails user2 = User
    //     .withUsername("user123")
    //     .password("password")
    //    // .roles("ADMIN","USER")
    //     .build();
    
    //     var inMemoryUserDetailsManager =new InMemoryUserDetailsManager(user1,user2);

    //         return inMemoryUserDetailsManager;

    // }
    @Autowired
    private oAuthAuthenticationSuccessHandler handler;

    @Autowired
    private SecurityCustomUserDetailService userDetailService;




    //configuration authentiacation provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        //user details service object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return  daoAuthenticationProvider;
    }




    //untilll that i can acees the signup page bcz it secure all page but we have to sign up
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{




        //configuration
        //urls ko configure kiya kon public rhega kon private
        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","/signup","/service").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });



        //form default loginpage
        ///agar kuch change krna h toh yha hoga  related form login
       httpSecurity.formLogin(formLogin->{
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        formLogin.successForwardUrl("/user/profile");
        //formLogin.failureForwardUrl("/login?error=true");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
       });



       //csrf protection always true intially
       httpSecurity.csrf(AbstractHttpConfigurer::disable);



       //logout configure here
       httpSecurity.logout(logoutForm->{
        logoutForm.logoutUrl("/logout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
       });


       //oauth configurations  like google facebook
       httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login");
        oauth.successHandler(handler);

       });


        return httpSecurity.build();

    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
