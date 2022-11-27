package com.blog.service.impl;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
import com.blog.utils.ImageUpload;
import com.blog.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Page toPage(List<UserDto> list, Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList,pageable, list.size());
    }

    @Override
    public Page<UserDto> pageUser(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(UserEntity userEntity:userEntities){
            userDtos.add(userConverter.toDto(userEntity));
        }
        Page<UserDto> pageUsers = toPage(userDtos,pageable);
        return pageUsers;
    }

    @Override
    public UserDto update(MultipartFile imageUser, UserDto userDto, String username) {
        try{
            UserEntity oldUserEntity = userRepository.findByUsername(username);
            UserEntity userEntity = userConverter.toEntity(oldUserEntity, userDto);

            if(imageUser.isEmpty()){
                userEntity.setAvatar(userEntity.getAvatar());
            }
            else {
                if(imageUpload.checkExisted(imageUser) == false){
                    imageUpload.uploadImage(imageUser);
                }
                userEntity.setAvatar(Base64.getEncoder().encodeToString(imageUser.getBytes()));
            }
            return userConverter.toDto(userRepository.save(userEntity));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countUser() {
        return userRepository.countUser();
    }

    @Override
    public void enabledById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.set_enabled(true);
        userRepository.save(userEntity);
    }

    @Override
    public void disabledById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.set_enabled(false);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userConverter.toDto(userEntity);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userConverter.toDto(userEntity);
    }

    @Override
    public void updatePassword(UserDto userDto, String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setPassword(passwordEncoder.encode(userDto.getNewpassword().trim()));
        userRepository.save(userEntity);
    }
}
