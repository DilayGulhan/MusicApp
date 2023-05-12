package com.researchecosystems.contentserviceapi.model.request.auth.register;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @Email(message = "Invalid Email!")
    @NotEmpty(message = "Email must be filled!")
    private String email;

    @Size(min = 6)
    @NotEmpty(message = "Password must be filled!")
    private String password;

}