package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RegUserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(UserDto dados){
        Optional<UserModel> userByCnpj = userRepository.findByUsername(dados.cnpj());
        Optional<UserModel> userByEmail = userRepository.findByEmail(dados.email());
        Optional<UserModel> userByUserSefaz = userRepository.findByUserSefaz(dados.userSefaz());

        if (userByCnpj.isPresent()) {
            throw new CnpjRegisteredException();
        }

        else if (userByEmail.isPresent()) {
            throw new EmailRegisteredException();
        }

        else if (userByUserSefaz.isPresent()) {
            throw new UserSefazRegisteredException();
        }

        userRepository.save(new UserModel(dados)); // !!!!!

        return "redirect:/login";
    }
}
