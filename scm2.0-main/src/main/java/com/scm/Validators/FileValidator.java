package com.scm.Validators;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; // 2MB
    // remmember a point i get error if i upload file greater thean limmit but limit is not set by application.properties field it is set by above limit if i do 5 it will set i do 10 then it is 10

    //so i set application to infinite that pagenot break and set limit from here i invest two are two debug this time 

    // type

    // height

    // width

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    
        if (file == null || file.isEmpty()) {
            // context.disableDefaultConstraintViolation();
            // context.buildConstraintViolationWithTemplate("File cannot be empty ").addConstraintViolation();
             return true;  // Returning false will ensure validation fails if the file is empty.
        }

        System.out.println("file size"+file.getSize());
    
        // file size check
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less then 2MB ").addConstraintViolation();
            return false;
        }
    
        // Add more checks like resolution here if necessary
    
        return true;
    }
}