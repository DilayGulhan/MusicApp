package com.researchecosystems.contentserviceapi.model.request.auth;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class ResetPasswordRequest {

    @NotEmpty(message = "Old password must be filled.")
    @Length(min = 6)
    private String oldPassword;

    @NotEmpty(message = "New Password must be filled.")
    @Length(min = 6)
    private String newPassword;
}
