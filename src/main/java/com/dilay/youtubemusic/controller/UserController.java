package com.dilay.youtubemusic.controller;

import com.dilay.youtubemusic.model.request.user.CreateUserRequest;
import com.dilay.youtubemusic.model.request.user.UpdateUserRequest;
import com.dilay.youtubemusic.model.response.InvoiceResponse;
import com.dilay.youtubemusic.model.response.user.UserResponse;
import com.dilay.youtubemusic.service.AuthenticationService;
import com.dilay.youtubemusic.service.UserService;
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
    @PostMapping("/{nameOfTheVideo}")
    public UserResponse likeVideo(@PathVariable String nameOfTheVideo){
        return userService.likeVideo( authenticationService.getAuthenticatedUserId(), nameOfTheVideo );


    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest , authenticationService.getAuthenticatedUserId());
    }

    @GetMapping("/getInvoice")
    public InvoiceResponse getInvoice(){
        return userService.getInvoiceOfTheUser(authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/update/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest , authenticationService.getAuthenticatedUserId());
    }

    @DeleteMapping("/delete/{userId}")
    public UserResponse deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId , authenticationService.getAuthenticatedUserId());
    }



}