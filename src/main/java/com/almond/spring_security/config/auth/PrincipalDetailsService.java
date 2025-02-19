package com.almond.spring_security.config.auth;


import com.almond.spring_security.dto.User;
import com.almond.spring_security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl("/login")을 통해 로그인을 진행하면
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername() 함수가 실

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper mapper;

    // Sequrity Session(내부 Authentication(내부 UserDetails))
    @Override                             // 프론트 form input name이 username과 이름이 같아야한다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mapper.findByUsername(username);
        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
