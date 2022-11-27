package com.blog.repository;

import com.blog.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findOneByName(String name);
    List<CategoryEntity> findAll();

    @Override
    Page<CategoryEntity> findAll(Pageable pageable);

    CategoryEntity findOneById(Long id);

    @Query("select c from CategoryEntity c where c.is_enabled=true")
    List<CategoryEntity> findAllEnabled();
}
