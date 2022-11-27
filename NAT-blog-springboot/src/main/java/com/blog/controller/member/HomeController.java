package com.blog.controller.member;

import com.blog.dto.CategoryDto;
import com.blog.dto.PostDto;
import com.blog.dto.UserDto;
import com.blog.service.CategoryService;
import com.blog.service.PostService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller(value = "homeControllerOfMember")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/member/index")
    public String homePage(Model model){
        return "redirect:/member/index/0";
    }

    @GetMapping("/member/index/{pageNo}")
    public String homePage(@PathVariable("pageNo")int pageNo, Model model){
        Page<PostDto> posts = postService.pagePostEnabled(pageNo);
        List<PostDto> postsTop5 = postService.selectTop5ByDate();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        List<String> categories = new ArrayList<>();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("title", "NAT Blog - Home Page");
        model.addAttribute("size",posts.getSize());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("posts", posts);
        model.addAttribute("postsTop5", postsTop5);
        model.addAttribute("categories", categories);
        model.addAttribute("nameOfPage", "Home Page");
        return "member/index";
    }

    @GetMapping("/member/categories/{pageNo}")
    public String postPageOfCategory(@PathVariable("pageNo")int pageNo,
                                     @RequestParam(name = "category")String category,
                                     Model model){
        Page<PostDto> posts = postService.pagePostCategoryAndEnabled(pageNo, category);
        //List<PostDto> postsTop5 = postService.selectTop5ByDate();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        List<String> categories = new ArrayList<>();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("title", "NAT Blog - Home Page");
        model.addAttribute("size",posts.getSize());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("posts", posts);
        model.addAttribute("postsTop5", null);
        model.addAttribute("categories", categories);
        model.addAttribute("nameOfPage", category);
        return "member/index";
    }

    @GetMapping("/member/blog-details/{id}")
    public String blogDetailsPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("title","NAT Blog - Blog details");
        model.addAttribute("nameOfPage", "Blog Detail Page");

        PostDto postDto = postService.findById(id);
        model.addAttribute("post", postDto);
        PostDto postPrev = postService.selectRandom();
        model.addAttribute("postPrev",postPrev);
        PostDto postNext = postService.selectRandom();
        model.addAttribute("postNext",postNext);

        UserDto author = userService.findByUsername(postDto.getUser());
        model.addAttribute("author", author);

        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        List<String> categories = new ArrayList<>();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("categories",categories);
        return "member/blog-details";
    }

    @GetMapping("/member/contact")
    public String contactPage(Model model){
        model.addAttribute("title","NAT Blog - Contact");
        model.addAttribute("nameOfPage", "Contact Page");
        return "member/contact";
    }


}
