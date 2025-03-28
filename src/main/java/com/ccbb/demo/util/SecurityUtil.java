package com.ccbb.demo.util;

import com.ccbb.demo.entity.UserJpaEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserJpaEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserJpaEntity) {
            return (UserJpaEntity) principal;
        } else {
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }
    }
}
