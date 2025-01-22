package com.ccbb.demo.service.redis;

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
    public void saveRefreshToken(String id, String refreshToken) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + id, refreshToken, 7, TimeUnit.DAYS); // Refresh Token 유효기간 7일
    }

    // Redis에서 Refresh Token 조회
    public String getRefreshToken(String id) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + id);
    }

    // Redis에서 Refresh Token 삭제
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
