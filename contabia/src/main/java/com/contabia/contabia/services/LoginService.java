package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.exceptions.IncorrectPasswordException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class LoginService {

    @Autowired
    private UserRepository userRepository; //repositório dos usuários
    
    public ResponseEntity<ExceptionMessage> verificaSenha(LoginDto dadosLogin){
        //variável que verifica se o CNPJ digitado está cadastrado no sistema 
        Optional<UserModel> user = userRepository.findByCnpj(dadosLogin.cnpj());

        if(user.isPresent()){
            if(user.get().getSenha().equals(dadosLogin.senha())){;
                return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
            }else{
                throw new IncorrectPasswordException(); //caso a senha digitada não esteja certa, retorna mensagem de erro
            }
        }

        throw new CnpjNotFoundException(); //caso o cnpj não esteja cadastrado no sistema
    }
}
