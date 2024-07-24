package com.contabia.contabia.exceptions;

/*
 * Classe Exception responsável por retornar uma mensagem de erro quando o CNPJ que foi requisitado não foi encontrado.
*/

public class CnpjNotFoundException extends RuntimeException{
    public CnpjNotFoundException(){
        super("CNPJ não encontrado"); //message
    }

    public CnpjNotFoundException(String message){
        super(message);
    }
}
