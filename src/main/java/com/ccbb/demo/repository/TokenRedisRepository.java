package com.ccbb.demo.repository;

import com.ccbb.demo.entity.TokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {
    Optional<TokenRedis> findByAccessToken(String accessToken);
}
