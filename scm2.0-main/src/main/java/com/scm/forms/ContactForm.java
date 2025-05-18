package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.Validators.ValidFile;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ContactForm {
    
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required If don't have  fill [example@gmail.com]")
    @Email(message="Invalid email syntax [example@gmail.com]")
    private String email;
    @NotBlank(message ="Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid Phone number")
    private String phoneNumber;
    @NotBlank(message = "Message required")
    private String address;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;

    //annotation create krenge jo file validate krega
    //size
    @ValidFile(message = "Please upload a valid file")
    private MultipartFile contactImage ;

    private String picture;

    
    



}
