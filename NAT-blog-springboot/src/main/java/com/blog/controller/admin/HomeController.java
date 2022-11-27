package com.blog.controller.admin;

import com.blog.dto.PostDto;
import com.blog.service.PostService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller(value = "homeControllerOfAdmin")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/index")
    public String index(Model model){
        model.addAttribute("title","Admin Dashboard");
        model.addAttribute("countPost", postService.countPost());
        model.addAttribute("countUser", userService.countUser());
        List<PostDto> postTop5 = postService.selectTop5ByDate();
        model.addAttribute("postTop5", postTop5);
        return "admin/index";
    }
}
