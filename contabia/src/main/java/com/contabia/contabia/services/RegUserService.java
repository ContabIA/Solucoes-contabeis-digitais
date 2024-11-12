package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RegUserService {

    @Autowired
    private UserRepository userRepository; //repository dos usuarios

    public ResponseEntity<ExceptionMessage> addUsuario(UserDto dados){
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
