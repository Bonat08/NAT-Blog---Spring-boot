package com.blog.controller.member;

import com.blog.dto.CategoryDto;
import com.blog.dto.CommentDto;
import com.blog.dto.PostDto;
import com.blog.dto.UserDto;
import com.blog.service.CategoryService;
import com.blog.service.CommentService;
import com.blog.service.PostService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller(value = "homeControllerOfMember")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

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

    @GetMapping("/member/search-post/{pageNo}")
    public String searchPostPage(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword")String keyword,
                                 Model model){
        Page<PostDto> posts = postService.searchPostList(pageNo, keyword);
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
        model.addAttribute("categories", categories);
        model.addAttribute("nameOfPage", "Home Page");

        model.addAttribute("keyword", keyword);
        return "member/search-post";
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

        List<CommentDto> commentDtos = commentService.findAllByPost(id);
        model.addAttribute("comments", commentDtos);
        model.addAttribute("countComments", commentService.countCommentOfPost(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);

        Date currentDate = new Date(System.currentTimeMillis());
        model.addAttribute("currentDate", currentDate);

        UserDto author = userService.findByUsername(postDto.getUser());
        model.addAttribute("author", author);

        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        List<String> categories = new ArrayList<>();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("categories",categories);
        model.addAttribute("countLikes", postService.countLikePost(id));
        if(username.equals("anonymousUser")){
            model.addAttribute("isLiked", "CANNOT");
        }
        else{
            model.addAttribute("isLiked", postService.checkIfUserLikedPost(id,username));
        }
        return "member/blog-details";
    }

    @GetMapping("/member/contact")
    public String contactPage(Model model){
        model.addAttribute("title","NAT Blog - Contact");
        model.addAttribute("nameOfPage", "Contact Page");
        return "member/contact";
    }


}
