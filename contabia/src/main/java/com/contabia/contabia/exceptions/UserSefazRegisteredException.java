package com.contabia.contabia.exceptions;

/*
 * Classe Exception responsável por retornar uma mensagem de erro quando o user Sefaz enviado pelo usuário já está cadastrado no sistema ContabIA.
*/

public class UserSefazRegisteredException extends RuntimeException{
    public UserSefazRegisteredException(){
        super("O usuário Sefaz informado já está cadastrado no ContabIA."); //message
    }

    public UserSefazRegisteredException(String message){
        super(message);
    }
}
