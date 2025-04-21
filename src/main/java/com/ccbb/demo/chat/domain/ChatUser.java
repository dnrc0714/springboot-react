package com.ccbb.demo.chat.domain;

import lombok.*;

import java.util.Date;



@Data
public class ChatUser {

    Long userId;

    Integer userTp;

    String nickname;

    String username;

    String phoneNumber;

    String id;

    String password;

    String email;

    String postCode;

    String roadAddress;

    String jibunAddress;

    String extraAddress;

    String region1st;

    String region2nd;

    String region3rd;

    String agreeYn;

    Date birthDate;

    String role;
}
