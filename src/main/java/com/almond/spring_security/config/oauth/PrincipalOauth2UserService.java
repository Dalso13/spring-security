package com.almond.spring_security.config.oauth;

import com.almond.spring_security.config.auth.PrincipalDetails;
import com.almond.spring_security.dto.User;
import com.almond.spring_security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// PrincipalDetailService 와 같은 역할, 다만 Oauth2 기능에서만 작동
// 0auth2 로그인 시 유저 정보가 DB에 없을시 강제로 DB에 저장
@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper mapper;

    // 로그인 진행시 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest 에는 사용자 정보가 담겨있다
        // 그중 username, email을 사용해서 회원가입을 시킨다.
        // username은 id가 암호화된 형태로 보내지는데 google_{id} 형태로 저장
        // password는 없으므로 임의의 값을 넣어준다.
        log.info("userRequest: {}", userRequest.toString());
        log.info("getClientRegistration: {}", userRequest.getClientRegistration());
        log.info("getAccessToken: {}", userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("getAttributes: {}", oAuth2User.getAttributes());

        // DB에 저장
        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String username = provider + "_" + providerId; // google_123123123123
        String role = "ROLE_USER";
        // 패스워드는 무작위 값 암호화 필자는 그냥 엑세스토큰 암호화해서 넣기
        String password = passwordEncoder.encode(userRequest.getAccessToken().getTokenValue());

        User user = mapper.findByUsername(username);
        if (user == null) {
            user = new User(username, password, role, email, provider, providerId);
            mapper.join(user);
            // id 값, date 값은 db에서 입력되서 입력된 값을 가져올려면 다시 select 해야한다.
            user = mapper.findByUsername(username);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
