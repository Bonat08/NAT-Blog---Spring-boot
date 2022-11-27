package com.blog.service;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import org.springframework.stereotype.Service;


public interface AuthService {
    UserEntity findByUsername(String username);
    UserEntity save(UserDto userDto);

    UserEntity saveAsAdmin(UserDto userDto);

}
