package com.ccbb.demo.service;

import com.ccbb.demo.entity.User;
import com.ccbb.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ccbb.demo.util.JwtUtil;
import java.util.concurrent.TimeUnit;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        String encryptedPassword = encoder.encode(password); // 비밀번호 암호화
        user.setPassword(encryptedPassword);
        user.setEmail(email);
        userRepository.save(user);

        // JWT 생성
        String token = jwtUtil.generateToken(username);

        // Redis에 토큰 저장
        long expirationInMillis = jwtUtil.getExpiration(token) - System.currentTimeMillis();
//        if (expirationInMillis > 0) {
//            redisTemplate.opsForValue().set(
//                    username,
//                    token,
//                    expirationInMillis, // 남은 만료 시간 (밀리초 단위),
//                    TimeUnit.MILLISECONDS
//            );
//        } else {
//            throw new IllegalStateException("Token has already expired");
//        }

        return token;
    }
}
