package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class Login {

    @GetMapping
    public String getLoginPage(@ModelAttribute("successMessage") final Object successMessage, Model model){
        model.addAttribute("isSuccess", successMessage);
        return "login";
    }
}
