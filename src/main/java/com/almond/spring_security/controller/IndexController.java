package com.almond.spring_security.controller;


import com.almond.spring_security.config.auth.PrincipalDetails;
import com.almond.spring_security.dto.User;
import com.almond.spring_security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@Slf4j
public class IndexController {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 일반 로그인 테스트
    @RequestMapping(path = {"/test/login"}, method = RequestMethod.GET)
    public @ResponseBody String testLogin(
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails useDetails) { // DI(의존성 주입)

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

//      log.info("authentication: {}", principalDetails.getUser());
//      log.info("authentication: {}", authentication.getPrincipal());
        log.info("useDetails: {}", useDetails.getUser());
        return "세션 정보 확인";
    }

    // OAuth 로그인 테스트
    // 둘다 Authentication 안에 들어갈 수 있지만 통일 시켜야한다.
    @RequestMapping(path = {"/test/oauth/login"}, method = RequestMethod.GET)
    public @ResponseBody String testOAuthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth) { // DI(의존성 주입)

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("authentication: {}", oAuth2User.getAttributes());
        log.info("oAuth2User: {}", oAuth.getAttributes());

        return "Oauth 세션 정보 확인";
    }

    @RequestMapping(path = {"/", ""}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails useDetails) {
        log.info("user: {}", useDetails.getUser());
        return "user";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public @ResponseBody String admin() {
        return "admin";
    }

    @RequestMapping(path = "/manager", method = RequestMethod.GET)
    public @ResponseBody String manager() {
        return "manager";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "loginForm";
    }

    @RequestMapping(path = "/join", method = RequestMethod.GET)
    public String joinForm() {
        return "joinForm";
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public String join(User user) {
        log.info("user: {}", user);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        mapper.join(user);
        return "redirect:/login";
    }

    @RequestMapping(path = "/joinProc", method = RequestMethod.GET)
    public @ResponseBody String joinProc() {
        return "회원가입 완료";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/data", method = RequestMethod.GET)
    public @ResponseBody String data() {
        return "데이터";
    }
}

