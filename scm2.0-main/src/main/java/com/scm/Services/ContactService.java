package com.scm.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.scm.entities.Contact;
import com.scm.entities.user;


public interface ContactService {
    //save contact
     Contact save(Contact contact);

     //deletecontact
     void delete(String id);

     //updatecontact
     Contact update(Contact contact);

     //deletecontact
     List<Contact>getAll();

     Contact getbyid(String Id);

     //search contact
    //List<Contact> search(String name ,String email,String phoneNumber);
    
     List<Contact> getByUserId(String userId);

     Page<Contact> getByUser(user username ,int page ,int size ,String sortBy, String order);

    public long getTotalContactsForUser(Long userId);
     


      //search contact
    Page<Contact> searchByName(String namekeyword,int page ,int size ,String sortBy, String order ,user user);
    Page<Contact> searchByPhoneNumber(String phoneNumberkeyword,int page ,int size ,String sortBy, String order,user user);
    Page<Contact> searchByEmail(String emailkeyword,int page ,int size ,String sortBy, String order,user user);



}
