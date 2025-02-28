package com.contabia.contabia.infra;

import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.exceptions.EmailRegisteredException;
import com.contabia.contabia.exceptions.IncorrectPasswordException;
import com.contabia.contabia.exceptions.UserSefazRegisteredException;

/*
 * Classe ControllerAdvice responsável por realizar o tratamento dos erros e retornar as mensagens na forma de um record ResponseMessage.
*/

@ControllerAdvice
public class ExceptionHandlerClass{

    @ExceptionHandler(CnpjRegisteredException.class) //tratamento no caso de uma CnpjRegisteredException
    private ResponseEntity<ResponseMessage> cnpjRegisteredHandler(CnpjRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(EmailRegisteredException.class) //tratamento no caso de uma EmailRegisteredException
    private ResponseEntity<ResponseMessage> emailRegisteredHandler(EmailRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(UserSefazRegisteredException.class) //tratamento no caso de uma UserSefazRegisteredException
    private ResponseEntity<ResponseMessage> userSefazRegisteredHandler(UserSefazRegisteredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(CnpjNotFoundException.class) //tratamento no caso de uma CnpjNotFoundException
    private ResponseEntity<ResponseMessage> cnpjNotFoundHandler(CnpjNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(IncorrectPasswordException.class) //tratamento no caso de uma IncorrectPasswordException
    private ResponseEntity<ResponseMessage> incorrectPasswordHandler(IncorrectPasswordException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseMessage> invalidCnpjHandler(MethodArgumentNotValidException e){

        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            if("cnpjEmpresa".equals(erro.getField()) || "cnpj".equals(erro.getField())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, erro.getDefaultMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST, "Erro de validação"));
    }

    @ExceptionHandler(MailConnectException.class)
    private ResponseEntity<ResponseMessage> mailConnectFailedHandler(MailConnectException e){
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ResponseMessage(HttpStatus.REQUEST_TIMEOUT, "Erro ao enviar e-mail, verifique sua conexão com a internet!"));
    }
}
