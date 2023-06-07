package com.researchecosystems.contentserviceapi.controller;

import com.researchecosystems.contentserviceapi.model.request.auth.ResetPasswordRequest;
import com.researchecosystems.contentserviceapi.model.request.user.CreateUserRequest;
import com.researchecosystems.contentserviceapi.model.request.user.UpdateUserRequest;
import com.researchecosystems.contentserviceapi.model.response.user.UserResponse;
import com.researchecosystems.contentserviceapi.service.AuthenticationService;
import com.researchecosystems.contentserviceapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    @ApiPageable
    public Page<UserResponse> listUsers(@ApiIgnore Pageable pageable ) {
        return userService.listUsers(pageable , authenticationService.getAuthenticatedUserId());
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(userId , authenticationService.getAuthenticatedUserId());
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest , authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest , authenticationService.getAuthenticatedUserId());
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId , authenticationService.getAuthenticatedUserId());
    }



}