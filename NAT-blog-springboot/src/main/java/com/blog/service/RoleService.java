package com.blog.service;

import com.blog.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto save(RoleDto roleDto);
    List<RoleDto> findAll();
    RoleDto findById(Long id);
    RoleDto findByName(String name);
    RoleDto update(RoleDto roleDto);

}
