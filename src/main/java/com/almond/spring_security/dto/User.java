package com.almond.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@ToString
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String email;
    private Timestamp createDate;
}
