package com.contabia.contabia.controllers;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/cadastro")
public class RegUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String cadastro() {
        return "cadastroUser";
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addUsuario(@Valid @RequestBody UserDto dados) {

        Map<String, String> resp = new HashMap<>();

        try {
            userRepository.save(new UserModel(dados));
            
            resp.put("resp", "deu bom");
            return ResponseEntity.ok().body(resp);

        } catch (DataIntegrityViolationException e) {
            Optional<UserModel> userByCnpj = userRepository.findByCnpj(dados.cnpj());
            Optional<UserModel> userByEmail = userRepository.findByEmail(dados.email());
            Optional<UserModel> userByUserSefaz = userRepository.findByUserSefaz(dados.userSefaz());

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
}
