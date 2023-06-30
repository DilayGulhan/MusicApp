package com.dilay.contentserviceapi.model.request.auth;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailRecoveryRequest {

    @Email(message = "Invalid email!")
    @NotEmpty(message = "Email must be filled.")
    private String email;

    @NotEmpty(message = "Code must be filled.")
    private String recoveryCode;

    @NotEmpty(message = "New Password must be filled.")
    private String newPassword;
}
