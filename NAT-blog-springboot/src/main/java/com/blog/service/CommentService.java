package com.blog.service;

import com.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllByPost(Long postId);
    CommentDto save(CommentDto commentDto, Long postId, String username);

    Long countCommentOfPost(Long postId);
}
