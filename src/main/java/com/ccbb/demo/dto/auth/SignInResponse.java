package com.ccbb.demo.dto.auth;

import com.ccbb.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {

    private String accessToken;
    private String refreshToken;

    private User user;
}
