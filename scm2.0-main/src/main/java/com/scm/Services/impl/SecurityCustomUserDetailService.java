package com.scm.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.Repositories.UserRepo;

@Service
public class SecurityCustomUserDetailService implements  UserDetailsService {

     @Autowired
     private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException{
        // apne user ko load kran h hme 

       return  userRepo.findByEmail(username)
       .orElseThrow(()-> new UsernameNotFoundException("user not found exception"+username));

    }


}
