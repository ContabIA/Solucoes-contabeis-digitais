package com.contabia.contabia.exceptions;

/*
 * Classe Exception responsável por retornar uma mensagem de erro quando a senha enviada pelo usuário no login está incorreta.
*/

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(){
        super("Senha incorreta"); //message
    }

    public IncorrectPasswordException(String message){
        super(message);
    }
}
