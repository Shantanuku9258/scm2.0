package com.scm.Services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.scm.Repositories.ContactRepo;
import com.scm.Services.ContactService;
import com.scm.entities.Contact;
import com.scm.entities.user;

import lombok.experimental.var;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId =UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);

         }


    @Override
    public void delete(String id) {
        Contact contact =contactRepo.findById(id).orElseThrow(()->new RuntimeException("user noot found"));
       contactRepo.delete(contact);
        }

    @Override
    public Contact update(Contact contact) {
         var contactOld=contactRepo.findById(contact.getId()).orElseThrow(()->new RuntimeException("contact Not Found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setFavourite(contact.isFavourite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setPicture(contact.getPicture());

        return contactRepo.save(contactOld);
        
      
        }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
          }

    @Override
    public Contact getbyid(String id) {
        return contactRepo.findById(id).orElseThrow(()->new RuntimeException("contact Not Found"+id));
       }

   

    @Override
    public List<Contact> getByUserId(String userId) {
         return contactRepo.findByUserId(userId);
          }


   


    @Override
    public Page<Contact> getByUser(user username, int page ,int size ,String sortBy, String direction) {
        Sort sort= direction.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);
        
        return contactRepo.findByUser(username,pageable);
       
    }

    @Override
    public Page<Contact> searchByName(String namekeyword, int size,int page,  String sortBy, String order,user user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    var pageable = PageRequest.of(page, size, sort);
    return contactRepo.findByUserAndNameContaining(user,  namekeyword,pageable);

    }


    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberkeyword,int size, int page,  String sortBy, String order,user user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberkeyword, pageable);
    
     }


    @Override
    public Page<Contact> searchByEmail(String emailkeyword, int size, int page,  String sortBy, String order,user user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailkeyword,pageable);}


    public long getTotalContactsForUser(Long userId) {
        return contactRepo.countByUserId(userId);
    }

   

}
