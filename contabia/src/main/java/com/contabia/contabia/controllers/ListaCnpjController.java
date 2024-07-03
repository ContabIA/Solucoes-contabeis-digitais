package com.contabia.contabia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/listaCnpj")
public class ListaCnpjController {

    @GetMapping
    public String getMethodName(@RequestParam("cnpj") String cnpj, Model model) {
        model.addAttribute("cnpj", cnpj);
        return "listaCnpj";
    }
    
}
