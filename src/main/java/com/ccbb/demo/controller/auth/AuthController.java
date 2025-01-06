package com.ccbb.demo.controller.auth;

import com.ccbb.demo.entity.User;
import com.ccbb.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String[] tokens = authService.authenticate(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("아이디 혹은 비밀번호가 틀립니다."); // 로그인 실패 시 오류 메시지
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        try {
            // Refresh Token을 사용하여 새로운 Access Token 발급
            String accessToken = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(accessToken);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String username) {
        // Redis에서 토큰 삭제
        authService.logout(username);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
