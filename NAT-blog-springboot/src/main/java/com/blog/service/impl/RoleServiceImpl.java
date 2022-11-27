package com.blog.service.impl;

import com.blog.dto.RoleDto;
import com.blog.entity.RoleEntity;
import com.blog.repository.RoleRepository;
import com.blog.service.RoleService;
import com.blog.utils.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public RoleDto save(RoleDto roleDto) {
        RoleEntity roleEntity = roleConverter.toEntity(roleDto);
        return roleConverter.toDto(roleRepository.save(roleEntity));
    }

    @Override
    public List<RoleDto> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(RoleEntity roleEntity:roleEntities){
            roleDtos.add(roleConverter.toDto(roleEntity));
        }
        return roleDtos;
    }

    @Override
    public RoleDto findById(Long id) {
        return roleConverter.toDto(roleRepository.findOneById(id));
    }

    @Override
    public RoleDto findByName(String name) {
        return roleConverter.toDto(roleRepository.findByName(name));
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        RoleEntity oldRoleEntity = roleRepository.findOneById(roleDto.getId());
        RoleEntity newRoleEntity = roleConverter.toEntity(oldRoleEntity,roleDto);
        return roleConverter.toDto(roleRepository.save(newRoleEntity));
    }
}
