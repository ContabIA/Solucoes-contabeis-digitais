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

    /*
     * Classe controller responsável por cadastrar um novo usuário ao banco de dados
     * 
     * rotas:
     *  /cadastro/ (GET) -> exibe a página de cadastro de usuários
     * 
     *  /cadastro/ (POST) -> coleta todos os dados enviados pelo usuário e os salva no banco de dados 
    */

@Controller
@RequestMapping("/cadastro")
public class RegUserController {

    @Autowired
    private UserRepository userRepository; //repository dos usuarios

    @GetMapping
    public String cadastro() {
        return "cadastroUser"; //exibe a página de cadastro de usuário
    }
    
    @PostMapping
    @Transactional
    public String addUsuario(@Valid UserDto dados){
        userRepository.save(new UserModel(dados));
        return "redirect:/"; //redireciona o usuário para a página de login
    }
}
