package com.jw.fashionreview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login","/send-code","verify-code","/css/**").permitAll()
                        .anyRequest().authenticated() // 나머지는 로그인 필요
                )
                .userDetailsService(customUserDetailsService)  // ✅ 연결
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // 로그인 처리 URL 추가
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 홈으로 이동
                        .failureUrl("/login?error=true") // 실패시 다시 로그인
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    // 비밀번호 암호화기 Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
