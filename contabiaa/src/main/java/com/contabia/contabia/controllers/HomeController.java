package com.contabia.contabia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping
    public String home() {
        return "home";
    }
}
