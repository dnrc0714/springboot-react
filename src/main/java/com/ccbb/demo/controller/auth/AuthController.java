package com.ccbb.demo.controller.auth;

import com.ccbb.demo.entity.User;
import com.ccbb.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
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
}
