package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String category;
    private String shortdescription;
    private Date createddate;
    private String image;
    private String user;
    private String content;
    private boolean is_enabled;
    private Long numOfComments;
}
