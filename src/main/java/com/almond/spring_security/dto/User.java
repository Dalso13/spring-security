package com.almond.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String email;
    private Timestamp createDate;
    private String provider, providerId;


    public User(String username, String password, String role, String email, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}
