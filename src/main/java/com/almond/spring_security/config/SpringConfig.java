package com.almond.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)  // Secured, preAuthorize, postAuthorize 어노테이션 활성화
public class SpringConfig {

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // CSRF 인증 비활성화
        http.csrf(AbstractHttpConfigurer::disable);
        // URL 자격인증 관리
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                // 시큐리티에서 url에서 이뤄지는 로그인을 대신 진행
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/"));


        return http.build();
    }
}
