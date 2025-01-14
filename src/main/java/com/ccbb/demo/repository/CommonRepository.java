package com.ccbb.demo.repository;

import com.ccbb.demo.entity.CmmCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommonRepository extends JpaRepository<CmmCode, Long> {
    Optional<CmmCode> findBySysCode(String sysCode);
}
