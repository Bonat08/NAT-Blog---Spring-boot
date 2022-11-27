package com.blog.service;

import com.blog.dto.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    List<CategoryDto> findAllEnabled();

    CategoryDto findById(Long id);

    CategoryDto findByName(String name);

    CategoryDto update(CategoryDto categoryDto);

    void disableById(Long id);

    void enableById(Long id);

    Page<CategoryDto> pageCategory(int pageNo);
}
