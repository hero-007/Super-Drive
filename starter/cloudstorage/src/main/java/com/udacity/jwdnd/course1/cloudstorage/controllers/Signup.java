package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class Signup {

    UserService userService;

    public Signup(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage(Model model){
        model.addAttribute("successMessage", false);
        model.addAttribute("errorMessage", false);
        model.addAttribute("newUser", new User());
        return "signup";
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute("newUser") User user, final RedirectAttributes redirectAttributes, Model model){
        Boolean successMessage = false;
        Boolean errorMessage = false;
        if(user != null){
            Integer userId = userService.createNewUser(user);
            if(userId != null && userId != -1){
                successMessage = true;
                redirectAttributes.addFlashAttribute("successMessage", successMessage);
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:login";
            }
        }
        errorMessage = true;
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("successMessage", successMessage);
        return "signup";
    }
}
