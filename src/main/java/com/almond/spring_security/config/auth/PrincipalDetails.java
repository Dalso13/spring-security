package com.almond.spring_security.config.auth;

import com.almond.spring_security.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;


// 로그인 진행이 완료되면 시큐리티 session을 만들어준다. (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함.
// User 오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)
@Data   // Getter, Setter           // 둘다 Authentication 안에 들어갈 수 있지만 통일 시키기 위해 참조
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 일반 로그인 사용자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인 사용자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return "";
    }
}
