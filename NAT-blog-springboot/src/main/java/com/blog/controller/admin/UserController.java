package com.blog.controller.admin;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import com.blog.repository.UserRepository;
import com.blog.service.AuthService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller(value = "userControllerOfAdmin")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/user")
    public String userPage(Model model){
        model.addAttribute("title", "User Management");
        return "redirect:/admin/user/0";
    }

    @GetMapping("/admin/user/{pageNo}")
    public String userPageable(@PathVariable("pageNo")int pageNo, Model model){
        model.addAttribute("title", "User Management");
        Page<UserDto> users = userService.pageUser(pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("users", users);
        return "admin/user";
    }

    @RequestMapping(value = "/enable-user/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String enableUser(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            userService.enabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen user has been enabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot enable chosen user !!");
        }
        return "redirect:/admin/user/0";
    }

    @RequestMapping(value = "/disable-user/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String disableUser(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try {
            userService.disabledById(id);
            redirectAttributes.addFlashAttribute("success", "Chosen user has been disabled");
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Cannot disable chosen user !!");
        }
        return "redirect:/admin/user/0";
    }

    @GetMapping("admin/add-admin")
    public String addAdminPage(Model model){
        model.addAttribute("title","Add new admin");
        model.addAttribute("userDto", new UserDto());
        return "admin/add-admin";
    }

    @PostMapping("/add-new-admin")
    public String addNewAdmin(@Valid @ModelAttribute("userDto") UserDto userDto,
                             BindingResult result,
                             Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "admin/add-admin";
            }
            UserEntity userEntity1 = userRepository.findByUsername(userDto.getUsername());
            if(userEntity1 != null){
                model.addAttribute("userDto", userDto);
                model.addAttribute("errors", "Username has been registered !");
                return "admin/add-admin";
            }
            UserEntity userEntity2 = userRepository.findByEmail(userDto.getEmail());
            if(userEntity2 != null){
                model.addAttribute("userDto", userDto);
                model.addAttribute("errors", "Email has been registered !");
                return "admin/add-admin";
            }

            if(userDto.getPassword().equals(userDto.getRepeatpassword())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword().trim()));
                authService.saveAsAdmin(userDto);
                model.addAttribute("userDto", userDto);
                model.addAttribute("success", "You has been registered successfully");
                //return "auth/login";
            }
            else{
                model.addAttribute("userDto", userDto);
                model.addAttribute("errors", "Repeat password has to be same as password !");
                return "admin/add-admin";
            }


        } catch (Exception e){
            model.addAttribute("errors","Something went wrong");
            e.printStackTrace();
        }
        return "admin/add-admin";
    }
}
