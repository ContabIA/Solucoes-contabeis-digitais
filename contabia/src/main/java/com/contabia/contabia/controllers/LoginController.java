package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String login() {
        return "loginUser";
    }

    @PostMapping
    public String autenticar(@Valid LoginDto dadosLogin) {
        Optional<UserModel> user = userRepository.findByCnpj(dadosLogin.cnpj());
        if(user.isPresent()){
            var userEncontrado = user.get();
            if(userEncontrado.getSenha().equals(dadosLogin.senha())){
                var cnpj = userEncontrado.getCnpj();
                return "redirect:/home?cnpj=" + cnpj;
            }
        }
        return "loginUser";
    }

}
