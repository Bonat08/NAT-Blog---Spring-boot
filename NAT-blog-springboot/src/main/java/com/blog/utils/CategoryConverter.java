package com.blog.utils;

import com.blog.dto.CategoryDto;
import com.blog.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {


    //TH: show info
    public CategoryDto toDto(CategoryEntity categoryEntity){
        CategoryDto categoryDto = new CategoryDto();
        if(categoryEntity != null){
            categoryDto.setId(categoryEntity.getId());
            categoryDto.setName(categoryEntity.getName());
            categoryDto.setCreateddate(categoryEntity.getCreateddate());
            categoryDto.set_enabled(categoryEntity.is_enabled());
        }
        else{
            categoryDto = null;
        }
        return categoryDto;
    }

    //TH: add new category
    public CategoryEntity toEntity(CategoryDto categoryDto){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.set_enabled(categoryDto.is_enabled());
        return categoryEntity;
    }

    //TH: update category
    public CategoryEntity toEntity(CategoryEntity oldCategory, CategoryDto categoryDto){
        oldCategory.setName(categoryDto.getName());
        return oldCategory;
    }
}
