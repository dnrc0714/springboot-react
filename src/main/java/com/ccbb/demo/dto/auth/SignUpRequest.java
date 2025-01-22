package com.ccbb.demo.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignUpRequest {
    private String id;
    private String password;
    private String passwordChk;
    private String nickname;
    private String username;
    private String phoneNumber;
    private String email;
    private Integer userTp;
    private String postCode;
    private String roadAddress;
    private String jibunAddress;
    private String extraAddress;
    private String region1st;
    private String region2nd;
    private String region3nd;
    private String agreeYn;
    private Date birthDate;

}
