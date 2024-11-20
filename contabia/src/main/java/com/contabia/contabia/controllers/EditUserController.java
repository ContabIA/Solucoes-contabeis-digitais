package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.UserDto;
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
    public String editDadosUser(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        editUserService.coletarDadosAtual(cnpjUser, model);
        return "editUser";
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> criarEdicao(@RequestParam ("cnpjUser") String cnpjUser, @Valid @RequestBody UserDto userDto){
        return editUserService.editarUsuario(cnpjUser, userDto);
    }
}
