package com.almond.spring_security.config.auth.provider;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes; // oauth2User.getAttributes()

    @Override
    public String getProviderId() {
       return attributes.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

}
