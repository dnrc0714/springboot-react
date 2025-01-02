package com.ccbb.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // .requestMatchers("/", "/home", "/public/**").permitAll()  // 로그인과 회원가입 경로는 접근 허용
                        .anyRequest().permitAll()  // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .disable()  // 기본 로그인 화면 비활성화
                )
                .logout(logout -> logout
                        .permitAll()  // 로그아웃은 누구나 접근 가능
                );
        return http.build();
    }
}