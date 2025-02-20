package com.almond.spring_security.config;

import com.almond.spring_security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)  // Secured, preAuthorize, postAuthorize 어노테이션 활성화
public class SpringConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    
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

        // 로그인
        http.formLogin(form -> form
                .loginPage("/login")
                // 시큐리티에서 url에서 이뤄지는 로그인을 대신 진행
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/"));

        // oauth2
        // oauth 진행 과정 1. 인증코드 요청 2. 인증코드로 액세스 토큰 요청
        // 3. 액세스 토큰으로 사용자 정보 요청 4. 사용자 정보로 자동 회원가입
        // 다만 구글에서 주는 사용자 정보가 빈약하기에 추가적인 정보(주소, 등급)가 필요하면 입력 과정 필요
        // 구글 로그인 완료시 액세스 토큰 + 사용자 정보를 한번데 받는다.
        http.oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        // 구글 로그인 완료 후 후처리 필요
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService)
                        )
        );

        return http.build();
    }
}
