package com.scm.helper;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
//You can use super to call a method from the superclass that has been overridden in the subclass.


        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not found");
    }

}
