package com.blog.controller.member.api;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentAPI {

    @Autowired
    private CommentService commentService;

    @PostMapping("/api/comments/save")
    private CommentDto save(@RequestBody CommentDto commentDto, @RequestParam("postid")Long postid, @RequestParam("username")String username){
        return commentService.save(commentDto, postid, username);
    }
}
