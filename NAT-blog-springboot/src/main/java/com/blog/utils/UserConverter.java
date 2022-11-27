package com.blog.utils;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDto toDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        if(userEntity != null){
            userDto.setPassword(userEntity.getPassword());
            userDto.set_enabled(userEntity.is_enabled());
            if(userEntity.getAvatar() == null){
                userDto.setAvatar(null);
            } else {
                userDto.setAvatar(userEntity.getAvatar());
            }
            userDto.setEmail(userEntity.getEmail());
            userDto.setId(userEntity.getId());
            userDto.setUsername(userEntity.getUsername());
            userDto.setCreateddate(userEntity.getCreateddate());
        } else{
            userDto = null;
        }
        return userDto;
    }

    public UserEntity toEntity (UserEntity oldUserEntity, UserDto userDto){
        oldUserEntity.setEmail(userDto.getEmail());
        return oldUserEntity;
    }
}
