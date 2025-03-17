package com.ccbb.demo.controller.auth;

import com.ccbb.demo.dto.auth.SignInRequest;
import com.ccbb.demo.dto.auth.SignUpRequest;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 API
    @PostMapping(value = "/register")
    public String[] register(@RequestBody SignUpRequest request) {
        return authService.register(request);
    }

    // 로그인
    @PostMapping(value = "/signIn")
    public ResponseEntity<?> login(@RequestBody SignInRequest request) {
        try {
            String[] tokens = authService.authenticate(request);
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("아이디 혹은 비밀번호가 틀립니다."); // 로그인 실패 시 오류 메시지
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        try {
            // Refresh Token을 사용하여 새로운 Access Token 발급
            String accessToken = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(accessToken);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("올바르지 못한 로그인 정보입니다.");
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String refreshToken) {
        // "Bearer " 접두사 제거
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }

        authService.logout(refreshToken);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @PostMapping(value = "/idDupChk")
    public ResponseEntity<?> IdDupChk(@RequestParam(name = "id") String userId) {
        return  ResponseEntity.ok(authService.isUserIdExists(userId));
    }

    @PostMapping(value = "/nicknameDupChk")
    public ResponseEntity<?> nicknameDupChk(@RequestParam(name = "nickname") String nickname) {
        return  ResponseEntity.ok(authService.isNicknameExists(nickname));
    }

    @PostMapping(value = "/emailDupChk")
    public ResponseEntity<?> emailDupChk(@RequestParam(name = "email") String email) {
        boolean isExists = authService.isEmailExists(email);
            return  ResponseEntity.ok(isExists);
    }

    @PostMapping(value = "/phoneNumberDupChk")
    public ResponseEntity<?> phoneNumberDupChk(@RequestParam(name = "phoneNumber") String phoneNumber) {
        boolean isExists = authService.isPhoneNumberExists(phoneNumber);
            return ResponseEntity.ok(isExists);
    }
}
