package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.websocket.OnMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

   // @NotBlank(message = "Name is required")
    @Size(min=3,message="Name is required min 3 Characters ")    
    private String name;
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email Required")
    private String email;
    @Size(min=6 ,message = "Password required min 6 Characters")
   
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min=8,max=12,message = "Invalid phone number len 8-12")
    private String phoneNumber;

}
