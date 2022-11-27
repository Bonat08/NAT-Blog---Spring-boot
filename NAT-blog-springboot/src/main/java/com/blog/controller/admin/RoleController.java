package com.blog.controller.admin;

import com.blog.dto.RoleDto;
import com.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/add-role")
    public String addRolePage(Model model){
        model.addAttribute("title", "Add new role");
        model.addAttribute("roleDto", new RoleDto());
        return "admin/add-role";
    }

    @PostMapping("/add-new-role")
    public String addNewRole(@ModelAttribute("roleDto")RoleDto roleDto,
                             BindingResult result,
                             Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/add-role";
            }
            RoleDto roleDto1 = roleService.findByName(roleDto.getName());
            if(roleDto1 != null){
                model.addAttribute("roleDto1", roleDto);
                model.addAttribute("errors","Role has existed !!");
                return "admin/add-role";
            }
            else{
                roleService.save(roleDto);
                model.addAttribute("roleDto1", roleDto);
                model.addAttribute("success", "Role has been added successfully");
                return "admin/add-role";
            }

        } catch (Exception e){
            model.addAttribute("errors","Something went wrong!");
            e.printStackTrace();
        }
        return "admin/add-role";
    }


    @GetMapping("/admin/roles")
    public String rolesPage(Model model){
        model.addAttribute("title", "Roles");
        List<RoleDto> roleDtos = roleService.findAll();
        model.addAttribute("size", roleDtos.size());
        model.addAttribute("roles", roleDtos);
        return "admin/roles";
    }

    @GetMapping("/admin/update-role/{id}")
    public String updateRolePage(@PathVariable("id")Long id,
                                 Model model){
        model.addAttribute("title","Update role");
        RoleDto roleDto = roleService.findById(id);
        model.addAttribute("roleDto", roleDto);
        return "admin/update-role";
    }

    @PostMapping("/update-old-role/{id}")
    public String updateRole(@PathVariable("id")Long id,
                             @ModelAttribute("roleDto")RoleDto roleDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/update-role";
            }
            roleDto.setId(id);
            RoleDto roleDto1 = roleService.findByName(roleDto.getName());
            if(roleDto1 != null){
                model.addAttribute("roleDto", roleDto);
                model.addAttribute("errors","Role has existed !!");
                return "admin/update-role";
            }
            else{
                roleService.update(roleDto);
                model.addAttribute("roleDto", roleDto);
                redirectAttributes.addFlashAttribute("success", "Chosen role has been updated successfully");
                return "redirect:/admin/roles";
            }

        } catch (Exception e){
            model.addAttribute("errors","Something went wrong!");
            e.printStackTrace();
        }
        return "admin/update-role";

    }
}
