package com.dilay.contentserviceapi.controller;

import com.dilay.contentserviceapi.model.request.auth.*;
import com.dilay.contentserviceapi.model.request.auth.register.RegisterRequest;
import com.dilay.contentserviceapi.model.response.LoginResponse;
import com.dilay.contentserviceapi.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/verify")
    public void verify(@Valid @RequestBody EmailVerificationRequest body) {
        authenticationService.verify(body);
    }

    @PostMapping("/verify/email")
    public void sendVerificationEmail(@Valid @RequestBody EmailRequest body) {
        authenticationService.sendVerificationEmail(body);
    }

    @PostMapping("/recover")
    public void recover(@Valid @RequestBody EmailRecoveryRequest body) {
        authenticationService.recovery(body);
    }

    @PostMapping("/recover/email")
    public void sendRecoveryEmail(@Valid @RequestBody EmailRequest body) {
        authenticationService.sendRecoveryEmail(body);
    }

}
