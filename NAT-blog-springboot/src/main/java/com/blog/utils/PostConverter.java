package com.blog.utils;

import com.blog.dto.PostDto;
import com.blog.entity.PostEntity;
import com.blog.repository.CategoryRepository;
import com.blog.repository.CommentRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDto toDto(PostEntity postEntity){
        PostDto postDto = new PostDto();
        if(postEntity == null){
            postDto = null;
        }
        else{
            postDto.setId(postEntity.getId());
            postDto.setTitle(postEntity.getTitle());
            postDto.setCategory(postEntity.getCategory().getName());
            postDto.setShortdescription(postEntity.getShortdescription());
            postDto.setContent(postEntity.getContent());
            postDto.setCreateddate(postEntity.getCreateddate());
            postDto.setUser(postEntity.getUser().getUsername());
            postDto.setImage(postEntity.getImage());
            postDto.set_enabled(postEntity.is_enabled());
            postDto.setNumOfComments(commentRepository.countAllByPost(postEntity));
        }
        return postDto;
    }

    public PostEntity toEntity(PostDto postDto){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setCategory(categoryRepository.findOneByName(postDto.getCategory()));
        postEntity.setUser(userRepository.findByUsername(postDto.getUser()));
        postEntity.setShortdescription(postDto.getShortdescription());
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }

    public PostEntity toEntity(PostEntity oldPostEntity, PostDto postDto){
        oldPostEntity.setTitle(postDto.getTitle());
        oldPostEntity.setCategory(categoryRepository.findOneByName(postDto.getCategory()));
        oldPostEntity.setShortdescription(postDto.getShortdescription());
        oldPostEntity.setContent(postDto.getContent());
        return oldPostEntity;
    }
}
