package com.blog.utils;

import com.blog.dto.RoleDto;
import com.blog.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public RoleDto toDto(RoleEntity roleEntity){
        RoleDto roleDto = new RoleDto();
        if(roleEntity != null){
            roleDto.setId(roleEntity.getId());
            roleDto.setName(roleEntity.getName());
            roleDto.setDescription(roleEntity.getDescription());
        }
        else{
            roleDto = null;
        }
        return roleDto;
    }

    public RoleEntity toEntity(RoleDto roleDto){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDto.getName());
        roleEntity.setDescription(roleDto.getDescription());
        return roleEntity;
    }

    public RoleEntity toEntity(RoleEntity oldRoleEntity, RoleDto roleDto){
        oldRoleEntity.setDescription(roleDto.getDescription());
        oldRoleEntity.setName(roleDto.getName());
        return oldRoleEntity;
    }
}
