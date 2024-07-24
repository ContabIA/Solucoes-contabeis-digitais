package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<ExceptionMessage> addUsuario(@Valid @RequestBody UserDto dados) {

        //variável para verificar se o CNPJ que está sendo cadastrado já existe no sistema
        Optional<UserModel> userByCnpj = userRepository.findByCnpj(dados.cnpj());

        //variável para verificar se o e-mail que está sendo cadastrao já existe no sistema
        Optional<UserModel> userByEmail = userRepository.findByEmail(dados.email());

        //variável para verificar se o usuário Sefaz que está sendo cadastrado já existe no sistema
        Optional<UserModel> userByUserSefaz = userRepository.findByUserSefaz(dados.userSefaz());

        if (userByCnpj.isPresent()) {
            throw new CnpjRegisteredException(); //exceção de CNPJ já cadastrado
        }

        else if (userByEmail.isPresent()) {
            throw new EmailRegisteredException(); //exceção de e-mail já cadastrado
        }

        else if (userByUserSefaz.isPresent()) {
            throw new UserSefazRegisteredException(); //exceção de usuário Sefaz já cadastrado
        }

        //se tudo estiver certo, será criado um novo usuário
        userRepository.save(new UserModel(dados));
        
        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
        
    }
}
