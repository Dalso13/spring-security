package com.almond.spring_security.config.auth.provider;

public interface OAuth2UserInfo {
    String getProviderId();
    String getEmail();
}
