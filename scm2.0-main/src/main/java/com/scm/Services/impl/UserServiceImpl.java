package com.scm.Services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.scm.Repositories.UserRepo;
import com.scm.Services.UserService;
import com.scm.entities.user;
import com.scm.helper.AppConstants;
import com.scm.helper.ResourceNotFoundException;



@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    //for save user we need repository
    @Autowired
    private UserRepo userRepo;
// a logger is an interface used to log messages at various levels, such as INFO, DEBUG, WARN, ERROR, and TRACE
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    

    @Override
    public user saveUser(user User) {
//Check if email already exists
// if (isUserExistsByEmail(User.getEmail())) {
//     // Log the error or throw an exception
//     logger.error("Email already exists: " + User.getEmail());
// throw new ResourceNotFoundException("Email is already in use, please try with a different one.");
// }
       // we have to generate id for user
      // user id have to geerate

       String userId = UUID.randomUUID().toString();    
       User.setUserId(userId);
       //password encode
       //user.setpassword(userid)
       User.setPassword(passwordEncoder.encode(User.getPassword()));

       //set the user role
       User.setRoleList(List.of(AppConstants.ROLE_USER));


      logger.info(User.getProvider().toString());
        return userRepo.save(User);
    }

    @Override
    public Optional<user> getUserById(String id) {
        return userRepo.findById(id);
        
       
    }

    @Override
    public Optional<user> updateUser(user User) {
      
        
       user user2= userRepo.findById(User.getUserId())
       .orElseThrow(() -> new ResourceNotFoundException("User not found"));

       //update user2 from user
         user2.setName(User.getName());
         user2.setEmail(User.getEmail());
         user2.setPassword(User.getPassword());
         user2.setAbout(User.getAbout());
         user2.setPhoneNumber(User.getPhoneNumber());
         user2.setProfilePic(User.getProfilePic());
         user2.setEmailverified(User.isEmailverified());
         user2.setPhoneverified(User.isPhoneverified());
         user2.setEnabled(User.isEnabled());
         user2.setProvider(User.getProvider());
         user2.setProviderUserId(User.getProviderUserId());

         //save the user on dataabase
            user save = userRepo.save(user2);
            return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        user user2= userRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExits(String userId) {
        user user2= userRepo.findById(userId).orElse(null);
        return user2 != null;

        }

    @Override
    public boolean isUserExistsByEmail(String email) {
       user user2= userRepo.findByEmail(email).orElse(null);
       return user2!=null;

          
    }

    @Override
    public List<user> getAllUsers() {
       return userRepo.findAll();
      }

    @Override
    public user getUserbyEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
        }
      
    }





// @Service
// public class UserServiceImpl implements UserService {
//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Autowired
//     private UserRepo userRepo;

//     private Logger logger = LoggerFactory.getLogger(this.getClass());

//     @Override
//     public user saveUser(user User) {
//         // Check if email already exists
//         if (isUserExistsByEmail(User.getEmail())) {
//             // Log the error or throw an exception
//             logger.error("Email already exists: " + User.getEmail());
//             throw new ResourceNotFoundException("Email is already in use, please try with a different one.");
//         }

//         // Generate user ID and encode password
//         String userId = UUID.randomUUID().toString();
//         User.setUserId(userId);
//         User.setPassword(passwordEncoder.encode(User.getPassword()));

//         // Set user role
//         User.setRoleList(List.of(AppConstants.ROLE_USER));

//         logger.info("Saving user with email: " + User.getEmail());
//         return userRepo.save(User);
//     }

//     @Override
//     public Optional<user> getUserById(String id) {
//         return userRepo.findById(id);
//     }

//     @Override
//     public Optional<user> updateUser(user User) {
//         user user2 = userRepo.findById(User.getUserId())
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         // Update user details
//         user2.setName(User.getName());
//         user2.setEmail(User.getEmail());
//         user2.setPassword(User.getPassword());
//         user2.setAbout(User.getAbout());
//         user2.setPhoneNumber(User.getPhoneNumber());
//         user2.setProfilePic(User.getProfilePic());
//         user2.setEmailverified(User.isEmailverified());
//         user2.setPhoneverified(User.isPhoneverified());
//         user2.setEnabled(User.isEnabled());
//         user2.setProvider(User.getProvider());
//         user2.setProviderUserId(User.getProviderUserId());

//         // Save the updated user
//         user save = userRepo.save(user2);
//         return Optional.ofNullable(save);
//     }

//     @Override
//     public void deleteUser(String id) {
//         user user2 = userRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//         userRepo.delete(user2);
//     }

//     @Override
//     public boolean isUserExits(String userId) {
//         user user2 = userRepo.findById(userId).orElse(null);
//         return user2 != null;
//     }

//     @Override
//     public boolean isUserExistsByEmail(String email) {
//         user user2 = userRepo.findByEmail(email).orElse(null);
//         return user2 != null; // Returns true if user exists with the given email
//     }

//     @Override
//     public List<user> getAllUsers() {
//         return userRepo.findAll();
//     }
// }
