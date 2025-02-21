package com.contabia.contabia.services;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.entity.ResetPasswordToken;
import com.contabia.contabia.repository.ResetPasswordTokenRepository;

@Service
public class ValidateResetPassTokenService {
    
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    public boolean isValidToken(String token){
        Optional<ResetPasswordToken> optionalToken = resetPasswordTokenRepository.findByToken(token);

        if(optionalToken.isPresent()){
            if(!isTokenExpired(optionalToken.get())){
                return true;
            }
        }
        return false;
    }

    public boolean isValidToken(ResetPasswordToken token){
        if(!isTokenExpired(token)){
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(ResetPasswordToken token){
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().before(calendar.getTime());
    }

}
