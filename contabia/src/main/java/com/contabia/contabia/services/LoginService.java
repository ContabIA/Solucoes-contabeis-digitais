package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.contabia.contabia.infra.ResponseMessage;
import com.contabia.contabia.models.dto.LoginDto;
import com.contabia.contabia.models.entity.ClientModel;
import com.contabia.contabia.repository.ClientRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class LoginService {

    @Autowired
    private ClientRepository clientRepository; //repositório dos usuários

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    public ResponseEntity<ResponseMessage> authenticationLogin(LoginDto dadosLogin, HttpServletResponse response){
        
        //variável que verifica se o CNPJ digitado está cadastrado no sistema 
        Optional<ClientModel> clientOptional = clientRepository.findByUsername(dadosLogin.cnpj());

        ResponseEntity<ResponseMessage> genericErrorMessage = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, "CNPJ ou senha incorretos"));

        if(clientOptional.isPresent()){
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dadosLogin.cnpj(), dadosLogin.senha());

            try{
                Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

                var token = tokenService.generateToken((User) authenticationResponse.getPrincipal());

                response.addCookie(generateAuthCookie(token));
            }
            catch(BadCredentialsException e){
                return genericErrorMessage;
            }
            
            //return "redirect:/home";
            return ResponseEntity.ok().body(new ResponseMessage(HttpStatus.OK, "ok"));
        }

        return genericErrorMessage;
    }

    private Cookie generateAuthCookie(String token){
        Cookie cookieAuth = new Cookie("Authorization", token);
        cookieAuth.setHttpOnly(true);
        cookieAuth.setPath("/");
        cookieAuth.setMaxAge(7200);

        return cookieAuth;
    }
}
