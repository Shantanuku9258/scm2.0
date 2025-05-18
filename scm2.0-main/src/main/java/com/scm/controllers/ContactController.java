package com.scm.controllers;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.Services.ContactService;
import com.scm.Services.UserService;
import com.scm.Services.impl.ImageService;
import com.scm.entities.Contact;
import com.scm.entities.user;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactsSearchForm;

import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/user/contacts")
public class ContactController {
     Logger logger = LoggerFactory.getLogger( ContactController.class);
    
    @Autowired
    private ContactService contactService;
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private UserService userService;
    
@RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        //contactForm.setFavourite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }


    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult rBindingResult,Authentication authentication, HttpSession session){

        //process form data
       String username = Helper.getEmailOfLoggedInuser(authentication);
       //validate the form

       if(rBindingResult.hasErrors()){
        session.setAttribute("message", Message.builder()
        .content("Please correct the following errors")
        .type(MessageType.red)
        .build());
        return "user/add_contact";
       }


        //form-> contact
        user User =userService.getUserbyEmail(username);

        //process the picture
        
        //image upload krne ka c 
        String filename  =UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);



        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setUser(User);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
       contact.setCloudinaryImagepublicId(filename);
        contactService.save(contact);
        
        
        session.setAttribute("message", Message.builder()
        .content("you have added contact successfully")
        .type(MessageType.green)
        .build());


        System.out.println(contactForm);
        //set the contact picture url

        return "redirect:/user/contacts/add";

    }




    //View contacts

    @RequestMapping
    public String viewcontacts(@RequestParam(value="page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = AppConstants.Page_size+"") int size,
                               @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
                               @RequestParam(value="direction", defaultValue = "asc") String direction,
                               Model model, Authentication authentication){


        //load alll the users
        String username = Helper.getEmailOfLoggedInuser(authentication);
        user user1 = userService.getUserbyEmail(username);
        Page<Contact> pagecontacts=contactService.getByUser(user1,page,size,sortBy,direction);
        model.addAttribute("pagecontacts",pagecontacts);
        model.addAttribute("pagesize",AppConstants.Page_size);
        model.addAttribute("contactSearchForm", new ContactsSearchForm());


        return "user/contacts";
                               }
           
                               


    //search handle
    @RequestMapping("/search")
    public String searchhandler(

        
    @ModelAttribute ContactsSearchForm contactSearchForm,
    @RequestParam(value = "size", defaultValue = AppConstants.Page_size+"") int size,
    @RequestParam(value="page", defaultValue = "0") int page,
    @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value="direction", defaultValue = "asc") String direction,
    Model model,  Authentication authentication){
        //before the authentication add it search the number or name in whole database  but lwe want to search
        //in the logged in user contacts only so we take a a looged in user for that we need to get the email of the logged in user

        logger.info("field{} keyword{}",contactSearchForm.getField(),contactSearchForm.getValue());

        var user = userService.getUserbyEmail(Helper.getEmailOfLoggedInuser(authentication));

        Page<Contact> pagecontacts=null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")){
            pagecontacts= contactService.searchByName(contactSearchForm.getValue(),size,page, sortBy, direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pagecontacts= contactService.searchByEmail(contactSearchForm.getValue(),size,page, sortBy, direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
            pagecontacts= contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page, sortBy, direction,user);
        }
        logger.info("pagecontacts {}",pagecontacts);
        model.addAttribute("contactSearchForm",  contactSearchForm);
      

        model.addAttribute("pagecontacts", pagecontacts);
        model.addAttribute("pagesize",AppConstants.Page_size);
       
        return "user/search";


    }



    //delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable("contactId") String contactId,
        HttpSession session
        ){
        contactService.delete(contactId);
        session.setAttribute("message", Message.builder()
        .content("contact deleted successfully")
        .type(MessageType.green).build());
       
        return "redirect:/user/contacts";
    }



   
@GetMapping("/view/{contactId}")
public String updateContactFormView(
        @PathVariable("contactId") String contactId,
        Model model) {

    var contact = contactService.getbyid(contactId);
    ContactForm contactForm = new ContactForm();
    contactForm.setName(contact.getName());
    contactForm.setEmail(contact.getEmail());
    contactForm.setPhoneNumber(contact.getPhoneNumber());
    contactForm.setAddress(contact.getAddress());
    contactForm.setDescription(contact.getDescription());
    contactForm.setFavourite(contact.isFavourite());
    contactForm.setWebsiteLink(contact.getWebsiteLink());
    contactForm.setLinkedInLink(contact.getLinkedInLink());
    
    contactForm.setPicture(contact.getPicture());
   
    
    model.addAttribute("contactForm", contactForm);
    model.addAttribute("contactId", contactId);

    return "user/update_contact_view";
}


@RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
public String updateContact(@PathVariable("contactId") String contactId, 
                             @Valid @ModelAttribute ContactForm contactForm,
                              Model model){

                                



                                // if (bindingResult.hasErrors()) {
                                //     return "user/update_contact_view";
                                // }


                                var con = new Contact();
                                con.setId(contactId);
                                con.setName(contactForm.getName());
                                con.setEmail(contactForm.getEmail());
                                con.setPhoneNumber(contactForm.getPhoneNumber());
                                con.setAddress(contactForm.getAddress());
                                con.setDescription(contactForm.getDescription());
                                con.setFavourite(contactForm.isFavourite());
                                con.setWebsiteLink(contactForm.getWebsiteLink());
                                con.setLinkedInLink(contactForm.getLinkedInLink());
                                con.setPicture(contactForm.getPicture());


                                  // update the contact
       

         //process image                       
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagepublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
              Contact existingContact = contactService.getbyid(contactId);
            con.setPicture(existingContact.getPicture()); 
        }

        
        var updatecon=contactService.update(con);
       
     model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());
         



   
    return "redirect:/user/contacts/view/"+contactId;
}

}