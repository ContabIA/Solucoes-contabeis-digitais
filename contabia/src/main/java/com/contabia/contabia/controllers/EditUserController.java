package com.contabia.contabia.controllers;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import com.contabia.contabia.models.entity.UserModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;

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
    public ResponseEntity<Map<String, String>> criarEdicao(@RequestParam ("cnpjUser") String cnpjUser, @Valid @RequestBody UserDto userDto){
        
        Optional<UserModel> userOptional = userRepository.findByCnpj(cnpjUser);

        Map<String, String> resp = new HashMap<>();
        
        if (userOptional.isPresent()){
            UserModel user = userOptional.get();
            user.editUser(userDto.cnpj(), userDto.email(), userDto.senha(), userDto.senhaSefaz(), userDto.userSefaz());


            try{
                userRepository.save(user);
                resp.put("resp", "deu bom");
                return ResponseEntity.ok().body(resp);
                
            } catch (DataIntegrityViolationException e) {

            Optional<UserModel> userByCnpj = userRepository.findByCnpj(userDto.cnpj());
            Optional<UserModel> userByEmail = userRepository.findByEmail(userDto.email());
            Optional<UserModel> userByUserSefaz = userRepository.findByUserSefaz(userDto.userSefaz());

            if (userByCnpj.isPresent()) {
                resp.put("resp", "O CNPJ informado já está cadastrado no ContabIA.");
            }

            else if (userByEmail.isPresent()) {
                resp.put("resp", "O E-mail informado já está cadastrado no ContabIA.");
            }

            else if (userByUserSefaz.isPresent()) {
                resp.put("resp","O usuário Sefaz informado já está cadastrado no ContabIA." );
            }

            else {
                resp.put("resp", "Erro ao cadastrar usuário. Confira as informações e tente novamente.");
                
            }

            return ResponseEntity.status(400).body(resp);
            }
            
        }
        return ResponseEntity.status(400).body(resp); 
    }


    


    
}
