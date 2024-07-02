package com.contabia.contabia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    
    @GetMapping
    public String login() {
        return "loginUser";
    }
    
}
