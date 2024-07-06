package com.contabia.contabia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping
    public String home(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        model.addAttribute("cnpjUser", cnpjUser);
        return "home";
    }
}
