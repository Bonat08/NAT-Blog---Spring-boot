package com.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {

    @GetMapping("/admin/comment")
    public String commentPage(Model model){
        model.addAttribute("title","Comments Management");
        return "admin/comment";
    }
}
