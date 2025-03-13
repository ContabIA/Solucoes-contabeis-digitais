package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.infra.ResponseMessage;
import com.contabia.contabia.models.dto.EditUserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class EditUserService {

    @Autowired
    private UserRepository userRepository;
    
    public void coletarDadosAtual(String cnpjUser, Model model){
        Optional<UserModel> userOptional = userRepository.findByUsername(cnpjUser);
        
        if (userOptional.isPresent()){
            UserModel user = userOptional.get();
            EditUserDto editUserDto = new EditUserDto(user.getUsername(), user.getEmail());
            model.addAttribute("usuario", editUserDto);
        }
    }

    public ResponseEntity<ResponseMessage> editarUsuario(String cnpjUser, EditUserDto userDto){

        Optional<UserModel> userOptional = userRepository.findByUsername(cnpjUser);
        Optional<UserModel> userByCnpj = userRepository.findByUsername(userDto.cnpj());
        Optional<UserModel> userByEmail = userRepository.findByEmail(userDto.email());

        UserModel user = userOptional.get();

        if (userByCnpj.isPresent() && !(userDto.cnpj().equals(cnpjUser))) {
            throw new CnpjRegisteredException();
        }

        else if (userByEmail.isPresent() && !(userDto.email().equals(user.getEmail()))) {
            throw new EmailRegisteredException();
        }

        user.editUser(userDto);
        userRepository.save(user);
        
        return ResponseEntity.ok().body(new ResponseMessage(HttpStatus.OK, "ok"));
    }
}
