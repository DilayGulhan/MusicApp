package com.researchecosystems.contentserviceapi.model.response.user;

import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class UserResponse {

    private String id;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    private String name;
    private String surname;
    private String email;
    private UserRole userRole;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();
    }

}
