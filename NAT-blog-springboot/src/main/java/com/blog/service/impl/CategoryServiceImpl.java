package com.blog.service.impl;

import com.blog.dto.CategoryDto;
import com.blog.entity.CategoryEntity;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import com.blog.utils.CategoryConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        categoryDto.set_enabled(true);
        CategoryEntity categoryEntity = categoryConverter.toEntity(categoryDto);
        return categoryConverter.toDto(categoryRepository.save(categoryEntity));
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> dtoList = new ArrayList<>();
        for(CategoryEntity entity: categoryRepository.findAll()){
            dtoList.add(categoryConverter.toDto(entity));
        }
        return dtoList;
    }

    @Override
    public List<CategoryDto> findAllEnabled() {
        List<CategoryDto> dtoList = new ArrayList<>();
        for(CategoryEntity entity: categoryRepository.findAllEnabled()){
            dtoList.add(categoryConverter.toDto(entity));
        }
        return dtoList;
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryConverter.toDto(categoryRepository.findOneById(id));
    }

    @Override
    public CategoryDto findByName(String name) {
        return categoryConverter.toDto(categoryRepository.findOneByName(name));
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        CategoryEntity oldCategory = categoryRepository.findOneById(categoryDto.getId());
        CategoryEntity newCategory = categoryConverter.toEntity(oldCategory,categoryDto);
        return categoryConverter.toDto(categoryRepository.save(newCategory));
    }

    @Override
    public void disableById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findOneById(id);
        categoryEntity.set_enabled(false);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void enableById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findOneById(id);
        categoryEntity.set_enabled(true);
        categoryRepository.save(categoryEntity);
    }

    private Page toPage(List<CategoryDto> list, Pageable pageable){
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
    public Page<CategoryDto> pageCategory(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<CategoryDto> categories = this.findAll();
        Page<CategoryDto> categoryPages = toPage(categories,pageable);
        return categoryPages;
    }
}
