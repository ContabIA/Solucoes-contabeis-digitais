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

    /*
     * Classe controller responsável por gerenciar a página de login e direcionar o usuário para as demais páginas (em versões futuras terá um sistema de autenticação para melhor segurança).
     * 
     * rotas:
     *  / (GET) -> exibe a página de login, sendo esta a primeira página da aplicação.
     * 
     *  / (POST) -> recebe e verifica as informações enviadas pelo usuário para que o mesmo entre em sua conta.
    */

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository; //repositório dos usuários
    
    @GetMapping
    public String login() {
        return "loginUser"; //retorna a página de login
    }

    @PostMapping
    public String autenticar(@Valid LoginDto dadosLogin) {
        //verifica se o cnpj e a senha digitada pelo usuário está correto. 
        Optional<UserModel> user = userRepository.findByCnpj(dadosLogin.cnpj());
        if(user.isPresent()){
            var userEncontrado = user.get();
            if(userEncontrado.getSenha().equals(dadosLogin.senha())){
                var cnpjUser = userEncontrado.getCnpj();
                return "redirect:/home?cnpjUser=" + cnpjUser;
            }
        }
        return "loginUser";
    }

}
