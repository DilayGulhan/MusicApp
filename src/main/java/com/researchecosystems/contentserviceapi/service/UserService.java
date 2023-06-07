package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.user.CreateUserRequest;
import com.researchecosystems.contentserviceapi.model.request.user.UpdateUserRequest;
import com.researchecosystems.contentserviceapi.model.response.user.UserResponse;
import com.researchecosystems.contentserviceapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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

    public Page<UserResponse> listUsers(Pageable pageable, String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }

        return userRepository.findAll(pageable).map(UserResponse::fromEntity);
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
