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
import com.contabia.contabia.models.entity.ClientModel;
import com.contabia.contabia.repository.ClientRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class LoginService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletResponse response;
    
    public ResponseEntity<ExceptionMessage> authenticateClient(LoginDto loginDetails){
        Optional<ClientModel> optionalClient = clientRepository.findByUsername(loginDetails.cnpj());

        if(optionalClient.isPresent()){
            return authenticate(loginDetails);
        }

        throw new CnpjNotFoundException();
    }

    private ResponseEntity<ExceptionMessage> authenticate(LoginDto loginDetails){
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginDetails.cnpj(), loginDetails.senha());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        var token = tokenService.generateToken((User) authenticationResponse.getPrincipal());

        response.addCookie(generateCookieToken(token));
        
        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
    }

    private Cookie generateCookieToken(String token){
        Cookie cookieToken = new Cookie("Authorization", token);
        cookieToken.setHttpOnly(true);
        cookieToken.setPath("/");
        cookieToken.setMaxAge(7200);
        return cookieToken;
    }
}
