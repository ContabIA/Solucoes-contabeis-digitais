package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.services.LoginService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

    /*
     * Classe controller responsável por gerenciar a página de login e direcionar o usuário para as demais páginas (em versões futuras terá um sistema de autenticação para melhor segurança).
     * 
     * rotas:
     *  / (GET) -> exibe a página de login, sendo esta a primeira página da aplicação.
     * 
     *  / (POST) -> recebe e verifica as informações enviadas pelo usuário para que o mesmo entre em sua conta.
    */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @GetMapping
    public String login() {
        return "loginUser"; //retorna a página de login
    }

    @PostMapping
    public ResponseEntity<ExceptionMessage> autenticar(@Valid @RequestBody LoginDto dadosLogin) {
        return loginService.verificaSenha(dadosLogin);
    }

}
