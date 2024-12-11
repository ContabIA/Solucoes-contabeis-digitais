package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
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
        Optional<UserModel> userOptional = userRepository.findByCnpj(cnpjUser);
        
        if (userOptional.isPresent()){
            UserModel user = userOptional.get();
            EditUserDto editUserDto = new EditUserDto(user.getCnpj(), user.getEmail(), user.getSenhaSefaz(), user.getUserSefaz());
            model.addAttribute("usuario", editUserDto);
        }
    }

    public ResponseEntity<ExceptionMessage> editarUsuario(String cnpjUser, EditUserDto userDto){

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

        user.editUser(userDto);
        userRepository.save(user);
        
        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
    }
}
