package com.scm.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.scm.entities.Contact;
import com.scm.entities.user;

import java.util.*;
@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
 

    Page<Contact> findByUser (user user, Pageable pageable); // Use 'user' as the parameter type


    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId") // Use 'user' (lowercase)
    List<Contact> findByUserId(@Param("userId") String userId); // Correct method name
    

    Page<Contact> findByUserAndNameContaining(user user,String namekeyword,Pageable  pageable);

    Page<Contact> findByUserAndEmailContaining(user user,String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(user user,String phonekeyword, Pageable pageable);


    // Count contacts for a specific user
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

}
 