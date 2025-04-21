package com.ccbb.demo.dto.auth;

import java.util.Date;

public record UserResponse(Long userId, Integer userTp, String nickname, String username, String phoneNumber
                            , String id, String password, String email, String postCode, String roadAddress
                            , String jibunAddress, String extraAddress, String region1st, String region2nd
                            , String region3rd, String agreeYn, Date birthDate, String role
) {
}
