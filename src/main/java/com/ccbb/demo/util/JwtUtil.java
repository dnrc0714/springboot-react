package com.ccbb.demo.util;

import com.ccbb.demo.service.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    private final RedisService redisService;
    private Key key; // JWT 서명을 위한 Key 객체 선언

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    public JwtUtil(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // Base64로 인코딩된 Secret Key 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes); // Secret Key를 이용하여 Key 객체 생성
    }

    public String generateAccessToken(Long userId, String id, String username, String nickName, String role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userId", userId)
                .claim("id", id)
                .claim("username", username)
                .claim("nickName", nickName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(Long userId, String id, String username, String nickName, String role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userId", userId)
                .claim("id", id)
                .claim("username", username)
                .claim("nickName", nickName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("JWT Token is missing");
            }

            // 2. "Bearer " 접두사가 있는 경우 제거
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 제거
            }

            return Jwts.parserBuilder()
                    .setSigningKey(key) // Secret Key 설정
                    .build()
                    .parseClaimsJws(token) // JWT 파싱
                    .getBody(); // Claims 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료된 토큰의 경우 클레임 정보 반환
        }
    }

    // 만료 시간 반환 메서드
    public long getExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration(); // 만료 시간 반환
        return expiration.getTime();
    }

    public Map<String, String> jwtData(String refreshToken) {
        Map<String, String> result = new HashMap<>();
        String userId = this.getClaims(refreshToken).getSubject();
        System.out.println("userId : " + userId);

        // Redis에서 해당 사용자의 Refresh Token 가져오기
        // 2. "Bearer " 접두사가 있는 경우 제거
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7); // "Bearer " 제거
        }

        String storedRefreshToken = redisService.getRefreshToken(userId);

        System.out.println("storedRefreshToken : " + storedRefreshToken);

        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
            try {
                var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(storedRefreshToken).getBody();

                String id = claims.get("id", String.class);
                String username = claims.get("username", String.class);
                String nickname = claims.get("nickname", String.class);
                String role = claims.get("role", String.class);

                System.out.println("------ : " + id);
                System.out.println("------ : " + username);


                result.put("userId", userId);
                result.put("id", id);
                result.put("username", username);
                result.put("nickname", nickname);
                result.put("role", role);

                return result;
            } catch (JwtException e) {
                throw new JwtException("expired refresh token");
            }
        }
        return result;
    }

    public boolean isValidToken(String token, String username) {
        return username.equals(getClaims(token).getSubject());
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Jws<Claims> decodeToken(String token) {
        try {
            return Jwts.parserBuilder().build().parseClaimsJws(token);
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
