package com.blog.controller.member;

import com.blog.dto.UserDto;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller(value = "userControllerOfMember")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/member/user-profile")
    public String userProfilePage(Model model){
        model.addAttribute("title", "NAT Blog - User Profile");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("userDto", user);
        model.addAttribute("userDtoPass", user);
        return "member/user-profile";
    }

    @PostMapping("/member/update-user-profile/{id}")
    public String userProfileUpdate(@PathVariable("id")Long id,
                                    @ModelAttribute("userDto")UserDto userDto,
                                    @RequestParam("imageUser") MultipartFile imageUser,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "redirect:/member/user-profile";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            UserDto userDto1 = userService.findByEmail(userDto.getEmail());
            if(userDto1 != null & userDto1.getUsername().equals(username) == false){
                redirectAttributes.addFlashAttribute("errors","Email has been registerd !!");
                return "redirect:/member/user-profile";
            }


            userService.update(imageUser,userDto,username);
            redirectAttributes.addFlashAttribute("success", "Your account has been updated");
            return "redirect:/member/user-profile";
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Something went wrong !!");
            return "redirect:/member/user-profile";
        }
    }

    @PostMapping("/member/change-password/{id}")
    public String userPasswordUpdate(@PathVariable("id")Long id,
                                    @ModelAttribute("userDto")UserDto userDto,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "redirect:/member/user-profile";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserDto oldUser = userService.findByUsername(username);

            if(passwordEncoder.matches(userDto.getCurrentpassword(), oldUser.getPassword()) == false){
                redirectAttributes.addFlashAttribute("errors","Current password is not correct!");
                return "redirect:/member/user-profile";
            }


            if(userDto.getRepeatpassword().equals(userDto.getNewpassword()) == false ){
                redirectAttributes.addFlashAttribute("errors","Repeat password has to be same as new password");
                return "redirect:/member/user-profile";
            }

            userService.updatePassword(userDto, username);
            redirectAttributes.addFlashAttribute("success", "Your password has been updated");
            return "redirect:/member/user-profile";
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Something went wrong !!");
            return "redirect:/member/user-profile";
        }
    }
}
