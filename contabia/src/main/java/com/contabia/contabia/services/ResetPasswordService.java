package com.contabia.contabia.services;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.ChangePasswordDto;
import com.contabia.contabia.models.entity.ResetPasswordToken;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ResetPasswordTokenRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Autowired
    private ValidateResetPassTokenService validatePassToken;

    public ResponseEntity<ExceptionMessage> saveNewPassword(ChangePasswordDto changePassData){
        Optional<ResetPasswordToken> optionalToken = resetPasswordTokenRepository.findByToken(changePassData.token());

        if(optionalToken.isPresent()){
            if(validatePassToken.isValidToken(optionalToken.get())){
                if(changePassData.password().equals(changePassData.confirmPassword())){
                    UserModel user = optionalToken.get().getUser();
                    user.updatePassword(changePassData.confirmPassword());
                    userRepository.save(user);

                    optionalToken.get().setExpiryDate(Calendar.getInstance().getTime());
                    resetPasswordTokenRepository.save(optionalToken.get());

                    return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "senha atualizada"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new ExceptionMessage(HttpStatus.BAD_REQUEST, "token inválido"));
    }

    public void forgotPassword(String userEmailAddress, HttpServletRequest request){
        Optional<UserModel> optionalUser = userRepository.findByEmail(userEmailAddress);


        if(optionalUser.isPresent()){
            Optional<ResetPasswordToken> optionalToken = resetPasswordTokenRepository.findByUser(optionalUser.get());

            if(optionalToken.isPresent()){
                updateAndSendPasswordToken(optionalUser.get(), optionalToken.get(), request);
            }
            else{
                createAndSendPasswordToken(optionalUser.get(), request);
            }
        }
    }

    private void createAndSendPasswordToken(UserModel user, HttpServletRequest request){
        String tokenString = UUID.randomUUID().toString();
        ResetPasswordToken myToken = new ResetPasswordToken(user, tokenString);
        resetPasswordTokenRepository.save(myToken);

        mailSender.send(constructResetTokenEmail(
                getAppUrl(request),
                request.getLocale(),
                tokenString,
                user.getEmail()
            )
        );
    }

    private void updateAndSendPasswordToken(UserModel user, ResetPasswordToken token, HttpServletRequest request){
        String tokenString = UUID.randomUUID().toString();
        var newExpiryDate = token.calculateExpiryDate();
        
        token.setToken(tokenString);
        token.setExpiryDate(newExpiryDate);

        resetPasswordTokenRepository.save(token);

        mailSender.send(constructResetTokenEmail(
                getAppUrl(request),
                request.getLocale(),
                tokenString,
                user.getEmail()
            )
        );
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, String userEmailAddress){
        String url = contextPath + "/redefinirSenha/novaSenha?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmailMessage("Redefinição da senha ContabIA", message + "\r\n" + url, userEmailAddress);
    }

    private SimpleMailMessage constructEmailMessage(String subject, String body, String userEmailAddress){
        SimpleMailMessage email = new SimpleMailMessage();
            email.setSubject(subject);
            email.setText(body);
            email.setTo(userEmailAddress);
            email.setFrom("adilsonfernandes588@gmail.com");
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() 
            + ":" + request.getServerPort() + "";
    }
}
