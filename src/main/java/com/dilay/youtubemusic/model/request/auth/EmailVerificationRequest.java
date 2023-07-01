package com.dilay.youtubemusic.model.request.auth;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class EmailVerificationRequest {

    @Email
    @NotEmpty(message = "Email must be filled")
    private String email;

    @NotEmpty(message = "Verification code must be filled")
    private String verificationCode;

    @Length(min = 6, message = "Password must contain at least 6 characters")
    @NotEmpty(message = "Password must be filled")
    private String password;
}
