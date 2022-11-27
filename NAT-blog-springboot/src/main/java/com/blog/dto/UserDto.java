package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    @Size(min = 3, max = 10, message = "Invalid Username! (3-10 characters)")
    private String username;

    @Size(min = 3, max = 10, message = "Invalid Password! (3-10 characters)")
    private String password;

    @Size(min = 3, max = 10, message = "Invalid Password! (3-10 characters)")
    private String repeatpassword;

    @Size(min = 3, max = 10, message = "Invalid Password! (3-10 characters)")
    private String currentpassword;

    @Size(min = 3, max = 10, message = "Invalid Password! (3-10 characters)")
    private String newpassword;

    private String email;

    private Long id;

    private boolean is_enabled;

    private String avatar;

    private Date createddate;

}
