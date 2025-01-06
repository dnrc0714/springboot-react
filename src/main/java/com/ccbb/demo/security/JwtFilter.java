package com.ccbb.demo.security;

import com.ccbb.demo.service.RedisService;
import com.ccbb.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Component
@WebFilter("/*")
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    // 인증이 필요 없는 URL 패턴들
    private static final List<String> UNAUTHENTICATED_URLS = Arrays.asList(
            "/", "/login", "/register", "/public/", "/auth/login"
    );

    // 정적 리소스 확장자들
    private static final List<String> STATIC_RESOURCE_EXTENSIONS = Arrays.asList(
            ".css", ".js", ".html", ".json", ".ico", ".png"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 URL 처리 (로그인, 회원가입, 정적 파일 등)
        if (isUnauthenticatedUrl(requestURI)) {
            filterChain.doFilter(request, response);  // 필터를 건너뛰고 계속 진행
            return;
        }

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거

            String username = jwtUtil.getClaims(token).getSubject();

            if (username != null && jwtUtil.validateToken(token)) {
                // Redis에서 토큰을 가져와서 일치하는지 확인
                String redisToken = redisService.getRefreshToken(username);

                if (token.equals(redisToken)) {
                    // 토큰이 유효하다면 인증 처리
                    // 필터 체인 계속 진행
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        // 유효하지 않은 토큰일 경우 401 오류
        response.setStatus(401);
        response.getWriter().write("Unauthorized");
    }

    // 인증이 필요 없는 URL인지 확인하는 메서드
    private boolean isUnauthenticatedUrl(String uri) {
        // 인증이 필요 없는 URL 처리
        if (UNAUTHENTICATED_URLS.contains(uri)) {
            return true;
        }

        // 정적 리소스 확장자 처리
        return STATIC_RESOURCE_EXTENSIONS.stream().anyMatch(uri::endsWith);
    }
}
