package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/cadastro")
public class RegUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String cadastro() {
        return "cadastroUser";
    }
    
    @PostMapping
    @Transactional
    public String addUsuario(@Valid UserDto dados){
        userRepository.save(new UserModel(dados));
        return "redirect:/";
    }
}
