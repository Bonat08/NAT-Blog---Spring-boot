package com.blog.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BlogErrorController implements ErrorController {

   private static final Logger LOGGER = LoggerFactory.getLogger(BlogErrorController.class);

   @RequestMapping("/error")
   public String handleError(HttpServletRequest request, Model model){
       Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

       if (status != null) {
           Integer statusCode = Integer.valueOf(status.toString());

           if(statusCode == HttpStatus.NOT_FOUND.value()) {
               String title = "Page not found";
               String errorCode = "404";
               String errorMessage = "The page you are looking for doesn't exist.";
               model.addAttribute("title", title);
               model.addAttribute("errorCode", errorCode);
               model.addAttribute("errorMessage", errorMessage);
           }
           else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
               String title = "Server error";
               String errorCode = "500";
               String errorMessage = "This page is not working";
               model.addAttribute("title", title);
               model.addAttribute("errorCode", errorCode);
               model.addAttribute("errorMessage", errorMessage);
           }
           else if (statusCode == HttpStatus.FORBIDDEN.value()) {
               String title = "Forbidden";
               String errorCode = "403";
               String errorMessage = "You don't have permission to access this page. ";
               model.addAttribute("title", title);
               model.addAttribute("errorCode", errorCode);
               model.addAttribute("errorMessage", errorMessage);
           }
           else {
               String title = "Error";
               String errorCode = "Error";
               String errorMessage = "Something went wrong. ";
               model.addAttribute("title", title);
               model.addAttribute("errorCode", errorCode);
               model.addAttribute("errorMessage", errorMessage);
           }
       }
       return "auth/errors";
   }

}
