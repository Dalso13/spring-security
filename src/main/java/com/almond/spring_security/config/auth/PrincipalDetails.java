package com.almond.spring_security.config.auth;

import com.almond.spring_security.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// 로그인 진행이 완료되면 시큐리티 session을 만들어준다. (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함.
// User 오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)
@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

    private User user;

    // 유저 권환 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Collection<GrantedAuthority> 타입이 필요하기에 ArayList에 구현된 인터페이스를 집어넣기
        // GrantedAuthority는 인터페이스 이며 getAuthority() 메소드에 리턴을 권환값을 넣어 오버라이딩 해서 구현
        Collection<GrantedAuthority> collect = List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
