package com.blog.controller.admin;

import com.blog.dto.CategoryDto;
import com.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/add-category")
    public String addCategoryPage(Model model){
        model.addAttribute("title","Add new category");
        model.addAttribute("categoryDto", new CategoryDto());
        return "admin/add-category";
    }

    @PostMapping("/add-new-category")
    public String addNewCategory(@ModelAttribute("categoryDto")CategoryDto categoryDto,
                                 BindingResult result,
                                 Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/add-category";
            }
            CategoryDto categoryDto1 = categoryService.findByName(categoryDto.getName());
            if(categoryDto1 != null){
                model.addAttribute("categoryDto", categoryDto);
                model.addAttribute("errors","Category has existed !!");
                return "admin/add-category";
            }
            else{
                categoryService.save(categoryDto);
                model.addAttribute("categoryDto", categoryDto);
                model.addAttribute("success", "Category has been added successfully");
                return "admin/add-category";
            }

        } catch (Exception e){
            model.addAttribute("errors","Something went wrong!");
            e.printStackTrace();
        }
        return "admin/add-category";
    }

//    @GetMapping("/admin/categories")
//    public String categoriesPage(Model model){
//        model.addAttribute("title","Categories");
//        List<CategoryDto> categoryDtoList = categoryService.findAll();
//        model.addAttribute("size",categoryDtoList.size());
//        model.addAttribute("categories", categoryDtoList);
//        return "admin/categories";
//    }

    @GetMapping("/admin/categories/{pageNo}")
    public String categoriesPagePaginated(@PathVariable("pageNo")int pageNo, Model model){
        Page<CategoryDto> categories =categoryService.pageCategory(pageNo);
        model.addAttribute("title","Categories");
        model.addAttribute("size",categories.getSize());
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @RequestMapping(value = "/enable-category/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String enableCategory(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen category has been enabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot enable chosen category !!");
        }
        return "redirect:/admin/categories/0";
    }

    @RequestMapping(value = "/disable-category/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String disableCategory(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            categoryService.disableById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen category has been disabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot disable chosen category !!");
        }
        return "redirect:/admin/categories/0";
    }

    @GetMapping("/admin/update-category/{id}")
    public String updateCategoryPage(@PathVariable("id")Long id, Model model){
        model.addAttribute("title","Update category");
        CategoryDto categoryDto = categoryService.findById(id);
        model.addAttribute("categoryDto", categoryDto);
        return "admin/update-category";
    }

    @PostMapping("/update-old-category/{id}")
    public String updateOldCategory(@ModelAttribute("categoryDto")CategoryDto categoryDto,
                                 @PathVariable("id")Long id,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/update-category";
            }
            categoryDto.setId(id);
            CategoryDto categoryDto1 = categoryService.findByName(categoryDto.getName());
            if(categoryDto1 != null){
                model.addAttribute("categoryDto", categoryDto);
                model.addAttribute("errors","Category has existed !!");
                return "admin/update-category";
            }
            else{
                categoryService.update(categoryDto);
                model.addAttribute("categoryDto", categoryDto);
                redirectAttributes.addFlashAttribute("success", "Chosen category has been updated successfully");
                return "redirect:/admin/categories";
            }

        } catch (Exception e){
            model.addAttribute("errors","Something went wrong!");
            e.printStackTrace();
        }
        return "admin/add-role";
    }
}
