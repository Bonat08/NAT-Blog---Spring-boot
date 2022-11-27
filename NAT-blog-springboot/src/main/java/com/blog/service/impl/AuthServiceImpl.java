package com.blog.service.impl;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRoles(Arrays.asList(roleRepository.findByName("MEMBER")));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity saveAsAdmin(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return userRepository.save(userEntity);
    }
}
