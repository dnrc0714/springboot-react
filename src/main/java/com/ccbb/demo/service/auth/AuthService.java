package com.ccbb.demo.service.auth;

import com.ccbb.demo.dto.auth.SignInRequest;
import com.ccbb.demo.dto.auth.SignUpRequest;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.repository.UserRepository;
import com.ccbb.demo.service.redis.RedisService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ccbb.demo.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public String[] register(SignUpRequest request) {
        if (userRepository.findById(request.getId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디 입니다.");
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임 입니다.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 등록된 이메일 입니다.");
        }

        if (userRepository.findByNickname(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("이미 등록된 휴대전화번호 입니다.");
        }

        UserJpaEntity user = UserJpaEntity.builder()
                .id(request.getId())
                .password(encoder.encode(request.getPassword()))
                .userTp(request.getUserTp())
                .nickname(request.getNickname())
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .postCode(request.getPostCode())
                .roadAddress(request.getRoadAddress())
                .jibunAddress(request.getJibunAddress())
                .extraAddress(request.getExtraAddress())
                .region1st(request.getRegion1st())
                .region2nd(request.getRegion2nd())
                .region3rd(request.getRegion3nd())
                .agreeYn(request.getAgreeYn())
                .birthDate(request.getBirthDate())
                .role("ROLE_USER")
                .build();

        UserJpaEntity saveUser = userRepository.save(user);
        
        // JWT 생성
        // JWT 토큰 생성 (Access Token)
        String accessToken = jwtUtil.generateAccessToken(saveUser.getUserId(), saveUser.getId(), saveUser.getUsername(), saveUser.getUsername(), saveUser.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(saveUser.getUserId(), saveUser.getId() , saveUser.getUsername(), saveUser.getUsername(), saveUser.getRole());


        // Redis에 토큰 저장
        long expirationInMillis = jwtUtil.getExpiration(refreshToken) - System.currentTimeMillis();
        if (expirationInMillis > 0) {
            redisService.saveRefreshToken(user.getUserId().toString(), refreshToken); // Redis에 Refresh Token 저장
        } else {
            throw new IllegalStateException("로그인 정보가 만료되었습니다.");
        }

        authenticateUser(user);

        return new String[]{accessToken, refreshToken};
    }

    @Transactional
    public String[] authenticate(SignInRequest request) {
        UserJpaEntity user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + request.getId()));

        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 혹은 비밀번호가 틀립니다.");
        }

        // Access Token과 Refresh Token 생성
        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getId(), user.getUsername(), user.getNickname(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId(), user.getId(), user.getUsername(), user.getNickname(), user.getRole());

        // Redis에 Refresh Token 저장
        redisService.saveRefreshToken(user.getUserId().toString(), refreshToken);

        authenticateUser(user);

        return new String[]{accessToken, refreshToken};
    }

    // Refresh Token을 사용하여 새로운 Access Token 발급
    public String refreshAccessToken(String refreshToken) {
        Map<String, String> jwtData = jwtUtil.jwtData(refreshToken);

        // Refresh Token이 유효하다면 새로운 Access Token 발급
        return jwtUtil.generateAccessToken(Long.parseLong(jwtData.get("userId")), jwtData.get("id"), jwtData.get("username"), jwtData.get("nickname"), jwtData.get("role"));
    }

    @Transactional
    public UserJpaEntity jwtTokenToUser(String token) {
        Map<String, String> jwtData = jwtUtil.jwtData(token);

        Optional<UserJpaEntity> optionalUser = userRepository.findById(jwtData.get("id"));

        return optionalUser.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public void logout(String refreshToken) {
        redisService.deleteRefreshToken(refreshToken);
    }

    @Transactional
    public boolean isUserIdExists(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    public boolean isNicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public void authenticateUser(UserJpaEntity user) {
        // ✅ 현재 사용자 정보를 SecurityContextHolder에 저장
        UsernamePasswordAuthenticationToken authenticationUser = new UsernamePasswordAuthenticationToken(user, null, List.of(() -> user.getRole()));
        SecurityContextHolder.getContext().setAuthentication(authenticationUser);
    }

}
