package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.auth.*;
import com.researchecosystems.contentserviceapi.model.request.auth.register.RegisterRequest;
import com.researchecosystems.contentserviceapi.model.response.LoginResponse;
import com.researchecosystems.contentserviceapi.repository.UserRepository;
import com.researchecosystems.contentserviceapi.security.JwtService;
import com.researchecosystems.contentserviceapi.service.client.EmailClient;
import com.researchecosystems.contentserviceapi.util.DateUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailClient emailClient;




    public String getAuthenticatedUserId() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            throw new BusinessException(ErrorCode.unauthorized, "Unauthorized user!");
        }
        return principal;
    }
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException(ErrorCode.account_already_exists, "Account already exists");
        }

        String verificationCode = RandomStringUtils.randomAlphanumeric(24);
        ZonedDateTime verificationCodeExpirationDate = DateUtil.now().plusDays(1);

        User user = new User();
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setUserRole(UserRole.USER);
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpirationDate(verificationCodeExpirationDate);
        user.setVerified(false);
        userRepository.save(user);

        emailClient.sendVerificationEmail(user);
        return user;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findFirstByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.password_mismatch, "Wrong Password");
        }

        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtService.createToken(user.getId()))
                .userRole(user.getUserRole())
                .build();
    }

    public Optional<User> getAuthenticatedUser() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return Optional.empty();
        }
        return userRepository.findById(principal);
    }

    public void sendVerificationEmail(EmailRequest emailRequest) {
        User user = userRepository.findFirstByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        if (user.isVerified()) {
            throw new BusinessException(ErrorCode.forbidden, "User is already verified");
        }

        String verificationCode = RandomStringUtils.randomAlphanumeric(24);
        ZonedDateTime verificationCodeExpirationDate = DateUtil.now().plusDays(1);

        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpirationDate(verificationCodeExpirationDate);
        userRepository.save(user);

        emailClient.sendVerificationEmail(user);
    }

    public void verify(EmailVerificationRequest emailVerificationRequest) {
        User user = userRepository.findFirstByEmail(emailVerificationRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        if (user.getVerificationCodeExpirationDate().isBefore(DateUtil.now())) {
            throw new BusinessException(ErrorCode.code_expired, "Verification code has expired");
        }

        if (!user.getVerificationCode().equals(emailVerificationRequest.getVerificationCode())) {
            throw new BusinessException(ErrorCode.code_mismatch, "Wrong verification code");
        }

        user.setRecoveryCodeExpirationDate(null);
        user.setRecoveryCode(null);
        user.setVerified(true);
        user.setPasswordHash(passwordEncoder.encode(emailVerificationRequest.getPassword()));
        userRepository.save(user);
    }

    public void recovery(EmailRecoveryRequest emailRecoveryRequest) {
        User user = userRepository.findFirstByEmail(emailRecoveryRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        if (user.getRecoveryCodeExpirationDate().isBefore(DateUtil.now())) {
            throw new BusinessException(ErrorCode.code_expired, "The code is expired!");
        }

        if (!user.getRecoveryCode().equals(emailRecoveryRequest.getRecoveryCode())) {
            throw new BusinessException(ErrorCode.code_mismatch, "The codes dont match!");
        }

        user.setRecoveryCode(null);
        user.setRecoveryCodeExpirationDate(null);
        user.setPasswordHash(passwordEncoder.encode(emailRecoveryRequest.getNewPassword()));

        userRepository.save(user);
    }

    public void sendRecoveryEmail(EmailRequest emailRequest) {
        User user = userRepository.findFirstByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        String recoveryCode = RandomStringUtils.randomAlphanumeric(24);
        ZonedDateTime recoveryCodeExpiredDate = DateUtil.now().plusDays(1);

        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpirationDate(recoveryCodeExpiredDate);
        userRepository.save(user);

        emailClient.sendRecoveryEmail(user);
    }

    public void resetPassword(Optional<User> userOptional, ResetPasswordRequest resetPasswordRequest) {
        User user = userOptional
                .orElseThrow(() -> new BusinessException(ErrorCode.account_missing, "User not found"));

        if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.password_mismatch, "Password mismatch.");
        }

        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

}
