package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class Login {

    @GetMapping
    public String getLoginPage(Model model){
        model.addAttribute("isLoggedOut", false);
        model.addAttribute("isError", false);
        return "login";
    }
}
