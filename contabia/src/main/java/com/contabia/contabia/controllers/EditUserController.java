package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.EditUserDto;
import com.contabia.contabia.services.EditUserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/editUser")
public class EditUserController {

    @Autowired
    private EditUserService editUserService;

    @GetMapping
    public String editDadosUser(Authentication authentication, Model model) {
        var cnpjUser = authentication.getName();
        editUserService.coletarDadosAtual(cnpjUser, model);
        return "editUser";
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> criarEdicao(Authentication authentication, @Valid @RequestBody EditUserDto editUserDto){
        var cnpjUser = authentication.getName();
        return editUserService.editarUsuario(cnpjUser, editUserDto);
    }
}
