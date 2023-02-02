package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private Long post_id;
    private UserDto user;
    private Date createddate;
}
