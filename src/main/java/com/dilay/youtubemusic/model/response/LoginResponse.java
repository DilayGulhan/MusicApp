package com.dilay.youtubemusic.model.response;

import com.dilay.youtubemusic.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class LoginResponse {

    private String id;
    private String token;
    private UserRole userRole;

}
