package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private Date createddate;

    private boolean is_enabled;
}
