package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class LoginService {

    @Autowired
    private UserRepository userRepository; //repositório dos usuários

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    public ResponseEntity<ExceptionMessage> authenticationLogin(LoginDto dadosLogin, HttpServletResponse response){
        //variável que verifica se o CNPJ digitado está cadastrado no sistema 
        Optional<UserModel> userOptional = userRepository.findByCnpj(dadosLogin.cnpj());

        if(userOptional.isPresent()){
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dadosLogin.cnpj(), dadosLogin.senha());
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

            var token = tokenService.generateToken((User) authenticationResponse.getPrincipal());

            response.addCookie(generateAuthCookie(token));
            
            //return "redirect:/home";
            return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
        }

        throw new CnpjNotFoundException(); //caso o cnpj não esteja cadastrado no sistema
    }

    private Cookie generateAuthCookie(String token){
        Cookie cookieAuth = new Cookie("Authorization", token);
        cookieAuth.setHttpOnly(true);
        cookieAuth.setPath("/");
        cookieAuth.setMaxAge(7200);

        return cookieAuth;
    }
}
