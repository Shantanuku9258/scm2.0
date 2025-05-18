package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Services.UserService;
import com.scm.entities.user;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.helper.ResourceNotFoundException;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class Pagecontoller {
    
@Autowired
@Qualifier("userServiceImpl")

    private UserService userService;
    @GetMapping("/")
    public static String index(){
        return "redirect:/home";
        }
    

   
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page handler");
        model.addAttribute("name","vikshit");
        model.addAttribute("githubrepository","https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/solutions/1251853/simple-java-solution-backtracking/");
        return "home";
    }
//always so run java during run dont click on run button
@RequestMapping("/about")
public static String aboutpage(){
    System.out.println("about page loading");
    return "about";
}
@RequestMapping("/service")
public static String servicepage(){
    System.out.println("service page loading");
    return "service";
}
//contact page

@GetMapping("/contact")
public static String contactpage(){
    System.out.println("contact page loading");
    return "contact";
}

//login registrtion
@GetMapping("/login")
public static String loginpage(){
    System.out.println("login page loading");
    return "login";
}

//this registration page
@GetMapping("/signup")
public static String signuppage(Model model){
    UserForm userForm = new UserForm();
    //set default value krte h 
    //userForm.setName("vikshit bhau");

    model.addAttribute("userForm", userForm);
    System.out.println("signup page loading");
    return "signup";
}

//preocessing register
@RequestMapping(value = "/do-register", method=RequestMethod.POST)
public String processregister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){ //userform ka object bna rhe h
    System.out.println("registering user");

    //fetch from data 
    //userform 
    System.out.println(userForm);
    //validate form data 
    //we use bindingresult and valid for validation
    if(rBindingResult.hasErrors()){
        return "signup";
    }


    //save to dataabase 

    // //userservices
    // user User = user.builder()
    // .name(userForm.getName())
    // .email(userForm.getEmail())
    // .password(userForm.getPassword())
    // .about(userForm.getAbout())
    // .phoneNumber(userForm.getPhoneNumber())
    // .profilePic("default.png")
    
    // .build();
    user User = new user();
    User.setName(userForm.getName());
    User.setEmail(userForm.getEmail());
    User.setPassword(userForm.getPassword());
    User.setAbout(userForm.getAbout());
    User.setPhoneNumber(userForm.getPhoneNumber());
    User.setProfilePic("default.png");



    user savedUser = userService.saveUser(User);
    System.out.println("user saved");


   //message =="registration successfull"
   //add message to the model throw which we can show message on the page
   Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();
  session.setAttribute("message", message);

    //redirect to login page
    

    return "redirect:/signup";
}


// @RequestMapping(value = "/do-register", method = RequestMethod.POST)
// public String processregister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
//     System.out.println("registering user");

//     // Clear previous session message
//     session.removeAttribute("message");

//     // Validate form data
//     if (rBindingResult.hasErrors()) {
//         return "signup";  // If validation errors, return to signup page
//     }

//     try {
//         // Attempt to save user
//         user newUser = new user();
//         newUser.setName(userForm.getName());
//         newUser.setEmail(userForm.getEmail());
//         newUser.setPassword(userForm.getPassword());  // Ensure password is hashed
//         newUser.setAbout(userForm.getAbout());
//         newUser.setPhoneNumber(userForm.getPhoneNumber());
//         newUser.setProfilePic("default.png");

//         user savedUser = userService.saveUser(newUser);  // Will throw exception if email is taken
//         System.out.println("user saved");

//         // Success message
//         Message message = Message.builder()
//                 .content("Registration successful!")
//                 .type(MessageType.green)
//                 .build();
//         session.setAttribute("message", message);

//         return "redirect:/signup";  // Redirect to login page after successful registration
//     } catch (ResourceNotFoundException e) {
//         // Handle duplicate email exception and show an error message
//         Message message = Message.builder()
//                 .content(e.getMessage())
//                 .type(MessageType.red)
//                 .build();
//         session.setAttribute("message", message);

//         return "redirect:/signup";  // Redirect back to signup page with error
//     }
// }

}
