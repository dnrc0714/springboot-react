package com.ccbb.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Entity
@Data
@Table(name = "health_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "회원구분은 필수 입력 값입니다.")
    @Column(name = "user_tp")
    private Integer userTp;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(name = "nickname")
    private String nickname;

    @NotBlank(message = "성명은 필수 입력 값입니다.")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Column(name = "id")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
       message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Column(name = "password")
    private String password;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Column(name = "email")
    private String email;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "jibun_address")
    private String jibunAddress;

    @Column(name = "extra_address")
    private String extraAddress;

    @Column(name = "region_1st")
    private String region1st;

    @Column(name = "region_2nd")
    private String region2nd;

    @Column(name = "region_3rd")
    private String region3rd;

    @Column(name = "agree_yn")
    private String agreeYn;
}
