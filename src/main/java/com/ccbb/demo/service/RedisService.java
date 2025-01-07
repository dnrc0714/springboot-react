package com.ccbb.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private static final String REFRESH_TOKEN_PREFIX = "REFRESH_TOKEN:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Refresh Token을 Redis에 저장
    public void saveRefreshToken(String username, String refreshToken) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + username, refreshToken, 7, TimeUnit.DAYS); // Refresh Token 유효기간 7일
    }

    // Redis에서 Refresh Token 조회
    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + username);
    }

    // Redis에서 Refresh Token 삭제
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
