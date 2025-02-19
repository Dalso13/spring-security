package com.almond.spring_security.mapper;

import com.almond.spring_security.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int join(User user);
    User findByUsername(String username);
}
