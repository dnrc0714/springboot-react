package com.ccbb.demo.util;

import com.ccbb.demo.entity.UserJpaEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserJpaEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserJpaEntity) {
            return (UserJpaEntity) principal;
        } else {
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }
    }
}
