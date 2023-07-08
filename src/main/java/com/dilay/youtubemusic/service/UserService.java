package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.Invoice;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.entity.Video;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.model.request.user.CreateUserRequest;
import com.dilay.youtubemusic.model.request.user.UpdateUserRequest;
import com.dilay.youtubemusic.model.response.InvoiceResponse;
import com.dilay.youtubemusic.model.response.user.UserResponse;
import com.dilay.youtubemusic.repository.UserRepository;
import com.dilay.youtubemusic.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VideoRepository videoRepository ;

    public Page<UserResponse> listUsers(Pageable pageable, String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }

        return userRepository.findAll(pageable).map(UserResponse::fromEntity);
    }



    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest ,String authenticatedUserId) {

        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        user.setUserRole(updateUserRequest.getUserRole());

        userRepository.save(user);

        return UserResponse.fromEntity(user);
    }
    public InvoiceResponse getInvoiceOfTheUser(String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));


        if (currentUser.getInvoice() == null) {
            throw new BusinessException(ErrorCode.resource_missing, "You haven't got any invoice");
        }
        Invoice invoice = currentUser.getInvoice();
        return InvoiceResponse.fromEntity(invoice);

    }

    public UserResponse likeVideo(String authenticatedUserId , String videoName){
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));
        Video video = videoRepository.findByTitle(videoName)
                        .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no video like that!"));
        currentUser.getFavoriteVideos().add(video);
        return UserResponse.fromEntity(currentUser);
    }

    public UserResponse getUser(String userId , String authenticatedUserId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) && !currentUser.equals(existingUser)) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        return UserResponse.fromEntity(user);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest ,String authenticatedUserId) {



        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException(ErrorCode.account_already_exists, "Account already exists");
        }

        User newUser = new User();
        newUser.setName(createUserRequest.getName());
        newUser.setSurname(createUserRequest.getSurname());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPasswordHash(createUserRequest.getPassword());
        newUser.setUserRole(createUserRequest.getUserRole());
        newUser.setVerified(false);

        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);
    }


    public UserResponse deleteUser(String userId ,String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found"));

        userRepository.delete(user);

        return UserResponse.fromEntity(user);
    }




}
