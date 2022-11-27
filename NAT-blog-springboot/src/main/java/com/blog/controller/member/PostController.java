package com.blog.controller.member;

import com.blog.dto.CategoryDto;
import com.blog.dto.PostDto;
import com.blog.service.CategoryService;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller(value = "postControllerOfMember")
public class PostController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;


    @GetMapping("member/add-post")
    public String addPostPageOfMember(Model model){
        List<String> categories = new ArrayList<>();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("title", "Add new post");
        model.addAttribute("categories", categories);
        model.addAttribute("postDto", new PostDto());
        return "member/add-post";
    }

    @PostMapping("/member/add-new-post")
    public String addNewPostOfMember(@ModelAttribute("product") PostDto postDto,
                              @RequestParam("imagePost") MultipartFile imagePost,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "member/add-post";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            postDto.setUser(authentication.getName());
            postService.save(imagePost, postDto);
            redirectAttributes.addFlashAttribute("success", "New post has been added");
            //model.addAttribute("postDto",postDto);
            return "redirect:/member/posts/0";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Something went wrong!");
        }
        return "member/add-post";
    }

    @GetMapping("member/posts/{pageNo}")
    public String postsPageOfMember(@PathVariable("pageNo")int pageNo, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<PostDto> posts = postService.pagePostOfUser(pageNo, authentication.getName());
        model.addAttribute("title", "Posts");
        model.addAttribute("size",posts.getSize());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("posts", posts);
        return "member/posts";
    }

    @GetMapping("member/update-post/{id}")
    public String updatePostPageOfMember(@PathVariable("id")Long id, Model model){
        model.addAttribute("title","Update post");
        PostDto postDto = postService.findById(id);
        model.addAttribute("postDto", postDto);
        List<String> categories = new ArrayList<>();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("categories", categories);
        return "member/update-post";
    }

    @PostMapping("/member/update-old-post/{id}")
    public String updateOldPostOfMember(@PathVariable("id")Long id,
                                @ModelAttribute("postDto")PostDto postDto,
                                @RequestParam("imagePost")MultipartFile imagePost,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "redirect:/member/posts/0";
            }
            postDto.setId(id);
            postService.update(imagePost, postDto);
            redirectAttributes.addFlashAttribute("success", "Post has been updated");
            //model.addAttribute("postDto",postDto);
            return "redirect:/member/posts/0";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Something went wrong");
            return "redirect:/member/posts/0";
        }
    }

    @RequestMapping(value = "/member/enable-post/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String enablePostOfMember(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            postService.enabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen post has been enabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot enable chosen post !!");
        }
        return "redirect:/member/posts/0";
    }

    @RequestMapping(value = "/member/disable-post/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String disablePostOfMember(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            postService.disabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen post has been disabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot disable chosen post !!");
        }
        return "redirect:/member/posts/0";
    }
}
