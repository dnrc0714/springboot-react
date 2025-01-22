package com.ccbb.demo.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String id;
    private String password;
}
