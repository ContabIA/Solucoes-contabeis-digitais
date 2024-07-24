package com.contabia.contabia.exceptions;

/*
 * Classe Exception responsável por retornar uma mensagem de erro quando o e-mail enviado pelo usuário já está cadastrado no sistema ContabIA.
*/

public class EmailRegisteredException extends RuntimeException{
    public EmailRegisteredException(){
        super("O E-mail informado já está cadastrado no ContabIA."); //message
    }

    public EmailRegisteredException(String message){
        super(message);
    }
}
