package com.ccbb.demo.repository;


import com.ccbb.demo.entity.UserJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findById(String id);

    Optional<UserJpaEntity> findByNickname(String nickname);

    Optional<UserJpaEntity> findByEmail(String email);

    Optional<UserJpaEntity> findByPhoneNumber(String phoneNumber);

}