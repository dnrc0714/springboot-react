package com.ccbb.demo.repository;

import com.ccbb.demo.entity.CmmCodeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonRepository extends JpaRepository<CmmCodeJpaEntity, Long> {
    List<CmmCodeJpaEntity> findBySysCodeOrderBySeq(String sysCode);
}
