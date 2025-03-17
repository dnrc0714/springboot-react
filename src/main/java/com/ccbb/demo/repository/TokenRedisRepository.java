package com.ccbb.demo.repository;

import com.ccbb.demo.entity.TokenRedisJpaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRedisRepository extends CrudRepository<TokenRedisJpaEntity, String> {
    Optional<TokenRedisJpaEntity> findByAccessToken(String accessToken);
}
