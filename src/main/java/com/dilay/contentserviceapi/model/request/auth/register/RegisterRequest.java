package com.dilay.contentserviceapi.model.request.auth.register;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class RegisterRequest {


    @NotEmpty(message = "name must be filled!")
    private String name;


    @NotEmpty(message = "surname must be filled!")
    private String surname;



    @Email(message = "Invalid Email!")
    @NotEmpty(message = "Email must be filled!")
    private String email;

    @Size(min = 6)
    @NotEmpty(message = "Password must be filled!")
    private String password;

}