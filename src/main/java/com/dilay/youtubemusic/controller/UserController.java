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
    @PostMapping("/like/{idOfTheVideo}")
    public UserResponse likeVideo(@PathVariable String idOfTheVideo){
        return userService.likeVideo( authenticationService.getAuthenticatedUserId(), idOfTheVideo );
    }

    @DeleteMapping("/dislike/{idOfTheVideo}")
    public UserResponse dislikeVideo(@PathVariable String idOfTheVideo){
        return userService.dislikeVideo( authenticationService.getAuthenticatedUserId(), idOfTheVideo );
    }

    @PostMapping("/follow/{idOfTheCategory}")
    public UserResponse followCategory(@PathVariable String idOfTheCategory){
        return userService.followCategory( authenticationService.getAuthenticatedUserId(), idOfTheCategory );
    }

    @DeleteMapping("/unfollow/{idOfTheCategory}")
    public UserResponse unfollowCategories(@PathVariable String idOfTheCategory){
        return userService.unfollowCategories( authenticationService.getAuthenticatedUserId(), idOfTheCategory );
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