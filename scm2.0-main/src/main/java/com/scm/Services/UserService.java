package com.scm.Services;

import java.util.*;

import com.scm.entities.user;

public interface UserService {
       //for save user
    user saveUser(user User);



 // what is optional->
 //A container object which may or may not contain a non-null value. If a value is present, isPresent() returns true.
 // If no value is present, the object is considered empty and isPresent() returns false.



    //for get user by id
    Optional<user> getUserById(String Id);

    //for update user value by id
    Optional<user> updateUser(user User);

    //for delete user by id
    void deleteUser(String id);

    //for check use r is exist or not by id 
    boolean isUserExits(String userId);

 //for check user is exist or not by email
    boolean isUserExistsByEmail(String email);

    // for get all user in a list
    List<user> getAllUsers();

    user getUserbyEmail(String email);
    
    


}
