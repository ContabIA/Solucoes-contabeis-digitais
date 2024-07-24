package com.contabia.contabia.exceptions;

/*
 * Classe Exception responsável por retornar uma mensagem de erro quando o CNPJ enviado pelo usuário já está cadastrado no sistema da ContabIA (tanto CNPJs de usuários como de empresas).
*/

public class CnpjRegisteredException extends RuntimeException{
    public CnpjRegisteredException(){
        super("O CNPJ informado já está cadastrado no ContabIA."); //message
    }

    public CnpjRegisteredException(String message){
        super(message);
    }
}
