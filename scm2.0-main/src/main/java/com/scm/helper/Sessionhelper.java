package com.scm.helper;

import org.apache.catalina.connector.Request;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class Sessionhelper {

    public static void removeMessage(){
        //this is used to delete the message when we refresh after showing the registaration successfull text through which we can remove
    try{
        System.out.println("removing message from seesion");
        HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("message");
    }
    catch(Exception e){
        System.out.println("Error in seesion helper"+e);
        e.printStackTrace();
    }

}
}
