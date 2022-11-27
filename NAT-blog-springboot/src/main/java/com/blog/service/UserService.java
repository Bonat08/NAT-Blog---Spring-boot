package com.blog.service;

import com.blog.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Page<UserDto> pageUser(int pageNo);

    UserDto update(MultipartFile imageUser, UserDto userDto, String username);

    int countUser();

    void enabledById(Long id);
    void disabledById(Long id);

    UserDto findByUsername(String username);

    UserDto findByEmail(String email);

    void updatePassword(UserDto userDto, String username);
}
