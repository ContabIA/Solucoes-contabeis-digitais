package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.exceptions.IncorrectPasswordException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

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
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository; //repositório dos usuários
    
    @GetMapping
    public String login() {
        return "loginUser"; //retorna a página de login
    }

    @PostMapping
    public ResponseEntity<ExceptionMessage> autenticar(@Valid @RequestBody LoginDto dadosLogin) {
        //variável que verifica se o CNPJ digitado está cadastrado no sistema 
        Optional<UserModel> user = userRepository.findByCnpj(dadosLogin.cnpj());

        if(user.isPresent()){
            if(user.get().getSenha().equals(dadosLogin.senha())){;
                return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
            }else{
                throw new IncorrectPasswordException(); //caso a senha digitada não esteja certa, retorna mensagem de erro
            }
        }

        throw new CnpjNotFoundException(); //caso o CNPJ digitado não esteja cadastrado, retorna mensagem de erro
    }

}
