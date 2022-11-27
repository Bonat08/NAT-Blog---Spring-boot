package com.blog.controller.auth;

import com.blog.dto.UserDto;
import com.blog.entity.UserEntity;
import com.blog.repository.UserRepository;
import com.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Login page");
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title", "Register page");
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register-new")
    public String addNewUser(@Valid @ModelAttribute("userDto")UserDto userDto,
                             BindingResult result,
                             Model model){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors","Something went wrong!");
                result.toString();
                return "auth/register";
            }
            UserEntity userEntity1 = userRepository.findByUsername(userDto.getUsername());
            if(userEntity1 != null){
                model.addAttribute("userDto", userDto);
                model.addAttribute("errors", "Username has been registered !");
                return "auth/register";
            }
            UserEntity userEntity2 = userRepository.findByEmail(userDto.getEmail());
            if(userEntity2 != null){
                model.addAttribute("userDto", userDto);
                model.addAttribute("errors", "Email has been registered !");
                return "auth/register";
            }
            else{
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                authService.save(userDto);
                model.addAttribute("userDto", userDto);
                model.addAttribute("success", "You has been registered successfully");
                //return "auth/login";
            }


        } catch (Exception e){
            model.addAttribute("errors","Something went wrong");
            e.printStackTrace();
        }
        return "auth/register";
    }
}
