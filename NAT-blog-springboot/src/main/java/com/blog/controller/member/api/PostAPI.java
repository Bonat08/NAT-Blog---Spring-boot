package com.blog.controller.member.api;

import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostAPI {

    @Autowired
    private PostService postService;

    @PostMapping("/api/posts/like")
    public void LikePost(@RequestParam("postid")Long postid, @RequestParam("username")String username){
        postService.likePost(postid, username);
    }

    @PostMapping("/api/posts/unlike")
    public void UnLikePost(@RequestParam("postid")Long postid, @RequestParam("username")String username){
        postService.unLikePost(postid, username);
    }
}
