package com.contabia.contabia.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.IncorrectPasswordException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;

/*
 * Classe ControllerAdvice responsável por realizar o tratamento dos erros e retornar as mensagens na forma de um record ExceptionMessage.
*/

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler{

    @ExceptionHandler(CnpjRegisteredException.class) //tratamento no caso de uma CnpjRegisteredException
    private ResponseEntity<ExceptionMessage> cnpjRegisteredHandler(CnpjRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(EmailRegisteredException.class) //tratamento no caso de uma EmailRegisteredException
    private ResponseEntity<ExceptionMessage> emailRegisteredHandler(EmailRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(UserSefazRegisteredException.class) //tratamento no caso de uma UserSefazRegisteredException
    private ResponseEntity<ExceptionMessage> userSefazRegisteredHandler(UserSefazRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(CnpjNotFoundException.class) //tratamento no caso de uma CnpjNotFoundException
    private ResponseEntity<ExceptionMessage> cnpjNotFoundHandler(CnpjNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(IncorrectPasswordException.class) //tratamento no caso de uma IncorrectPasswordException
    private ResponseEntity<ExceptionMessage> incorrectPasswordHandler(IncorrectPasswordException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
