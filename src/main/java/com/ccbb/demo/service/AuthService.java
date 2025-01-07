package com.ccbb.demo.service;

import com.ccbb.demo.entity.User;
import com.ccbb.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ccbb.demo.util.JwtUtil;
import java.util.concurrent.TimeUnit;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        String username = user.getUsername();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        // JWT 생성
        // JWT 토큰 생성 (Access Token)
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        // Redis에 토큰 저장
        long expirationInMillis = jwtUtil.getExpiration(refreshToken) - System.currentTimeMillis();
        if (expirationInMillis > 0) {
            redisService.saveRefreshToken(user.getUsername(), refreshToken); // Redis에 Refresh Token 저장
        } else {
            throw new IllegalStateException("Token has already expired");
        }

        return accessToken;
    }

    public String[] authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        if(!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("아이디 혹은 비밀번호가 틀립니다.");
        }


        // Access Token과 Refresh Token 생성
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        // Redis에 Refresh Token 저장
        redisService.saveRefreshToken(username, refreshToken);

        return new String[]{accessToken, refreshToken};
    }

    // Refresh Token을 사용하여 새로운 Access Token 발급
    public String refreshAccessToken(String refreshToken) {
        String username = jwtUtil.getClaims(refreshToken).getSubject();

        // Redis에서 해당 사용자의 Refresh Token 가져오기
        String storedRefreshToken = redisService.getRefreshToken(username);

        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
            // Refresh Token이 유효하다면 새로운 Access Token 발급
            return jwtUtil.generateAccessToken(username);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public void logout(String refreshToken) {
        redisService.deleteRefreshToken(refreshToken);
    }
}
