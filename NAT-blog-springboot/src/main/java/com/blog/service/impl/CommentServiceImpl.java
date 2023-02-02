package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.entity.CommentEntity;
import com.blog.entity.PostEntity;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import com.blog.utils.CommentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentConverter commentConverter;

    @Override
    public List<CommentDto> findAllByPost(Long postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        List<CommentEntity> commentEntities = commentRepository.findAllByPost(postEntity);
        List<CommentDto> commentDtos = new ArrayList<>();
        for(CommentEntity commentEntity:commentEntities){
            CommentDto commentDto = commentConverter.toDto(commentEntity);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    @Override
    public CommentDto save(CommentDto commentDto, Long postId, String username) {
        CommentEntity commentEntity = commentConverter.toEntity(commentDto, postId, username);
        return commentConverter.toDto(commentRepository.save(commentEntity));
    }

    @Override
    public Long countCommentOfPost(Long postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        return commentRepository.countAllByPost(postEntity);
    }
}
