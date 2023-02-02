package com.blog.utils;

import com.blog.dto.CommentDto;
import com.blog.dto.UserDto;
import com.blog.entity.CommentEntity;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentDto toDto(CommentEntity commentEntity){
        CommentDto commentDto = new CommentDto();
        if(commentEntity == null){
            commentDto = null;
        }
        else {
            commentDto.setContent(commentEntity.getContent());
            commentDto.setCreateddate(commentEntity.getCreateddate());
            commentDto.setPost_id(commentEntity.getPost().getId());
            UserDto userDto = userConverter.toDto(commentEntity.getUser());
            commentDto.setUser(userDto);
        }
        return commentDto;
    }

    public CommentEntity toEntity(CommentDto commentDto, Long postId, String username){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setUser(userRepository.findByUsername(username));
        commentEntity.setPost(postRepository.getReferenceById(postId));
        return commentEntity;
    }
}
