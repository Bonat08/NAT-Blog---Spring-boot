package com.blog.repository;

import com.blog.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
    RoleEntity findOneById(Long id);

    @Override
    List<RoleEntity> findAll();
}
