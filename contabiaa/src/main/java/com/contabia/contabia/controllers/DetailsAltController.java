package com.contabia.contabia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/detalhes")
public class DetailsAltController {
    
    @GetMapping()
    public String detalhe() {
        return "detalhes";
    }
    
}
