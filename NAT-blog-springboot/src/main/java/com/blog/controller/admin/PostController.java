package com.blog.controller.admin;

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

@Controller(value = "postControllerOfAdmin")
public class PostController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;


    @GetMapping("admin/add-post")
    public String addPostPage(Model model){
        List<String> categories = new ArrayList<>();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("title", "Add new post");
        model.addAttribute("categories", categories);
        model.addAttribute("postDto", new PostDto());
        return "admin/add-post";
    }

    @PostMapping("/add-new-post")
    public String addNewPost(@ModelAttribute("product") PostDto postDto,
                              @RequestParam("imagePost") MultipartFile imagePost,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes){

        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/add-post";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            postDto.setUser(authentication.getName());
            postService.save(imagePost, postDto);
            redirectAttributes.addFlashAttribute("success", "New post has been added");
            //model.addAttribute("postDto",postDto);
            return "redirect:/admin/posts/0";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Something went wrong!");
        }
        return "admin/add-post";
    }

    @GetMapping("admin/posts/{pageNo}")
    public String postsPage(@PathVariable("pageNo")int pageNo, Model model){
        Page<PostDto> posts = postService.pagePost(pageNo);
        model.addAttribute("title", "Posts");
        model.addAttribute("size",posts.getSize());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @GetMapping("admin/update-post/{id}")
    public String updatePostPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("title","Update post");
        PostDto postDto = postService.findById(id);
        model.addAttribute("postDto", postDto);
        List<String> categories = new ArrayList<>();
        List<CategoryDto> categoryDtos = categoryService.findAllEnabled();
        for(CategoryDto categoryDto:categoryDtos){
            categories.add(categoryDto.getName());
        }
        model.addAttribute("categories", categories);
        return "admin/update-post";
    }

    @PostMapping("/update-old-post/{id}")
    public String updateOldPost(@PathVariable("id")Long id,
                                @ModelAttribute("postDto")PostDto postDto,
                                @RequestParam("imagePost")MultipartFile imagePost,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "redirect:/admin/posts/0";
            }
            postDto.setId(id);
            postService.update(imagePost, postDto);
            redirectAttributes.addFlashAttribute("success", "Post has been updated");
            //model.addAttribute("postDto",postDto);
            return "redirect:/admin/posts/0";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Something went wrong");
            return "redirect:/admin/posts/0";
        }
    }

    @RequestMapping(value = "/enable-post/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String enablePost(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            postService.enabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen post has been enabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot enable chosen post !!");
        }
        return "redirect:/admin/posts/0";
    }

    @RequestMapping(value = "/disable-post/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String disablePost(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            postService.disabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen post has been disabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot disable chosen post !!");
        }
        return "redirect:/admin/posts/0";
    }
}
