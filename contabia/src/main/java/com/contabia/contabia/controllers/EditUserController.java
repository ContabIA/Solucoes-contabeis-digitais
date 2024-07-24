package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.contabia.contabia.models.entity.UserModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/editUser")
public class EditUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String editDadosUser(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        Optional<UserModel> userOptional = userRepository.findByCnpj(cnpjUser);
        
        if (userOptional.isPresent()){
            UserModel user = userOptional.get();
            UserDto userDto = new UserDto(user.getCnpj(), user.getEmail(), user.getSenha(), user.getSenhaSefaz(), user.getUserSefaz());
            model.addAttribute("usuario", userDto);
        }
        return "editUser";
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> criarEdicao(@RequestParam ("cnpjUser") String cnpjUser, @Valid @RequestBody UserDto userDto){
        
        Optional<UserModel> userOptional = userRepository.findByCnpj(cnpjUser);

        Optional<UserModel> userByCnpj = userRepository.findByCnpj(userDto.cnpj());
        Optional<UserModel> userByEmail = userRepository.findByEmail(userDto.email());
        Optional<UserModel> userByUserSefaz = userRepository.findByUserSefaz(userDto.userSefaz());

        UserModel user = userOptional.get();

        if (userByCnpj.isPresent() && !(userDto.cnpj().equals(cnpjUser))) {
            throw new CnpjRegisteredException();
        }

        else if (userByEmail.isPresent() && !(userDto.email().equals(user.getEmail()))) {
            throw new EmailRegisteredException();
        }

        else if (userByUserSefaz.isPresent() && !(userDto.userSefaz().equals(user.getUserSefaz()))) {
            throw new UserSefazRegisteredException();
        }

        user.editUser(userDto.cnpj(), userDto.email(), userDto.senha(), userDto.senhaSefaz(), userDto.userSefaz());
        userRepository.save(user);
        
        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
    }
}
