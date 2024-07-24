package com.contabia.contabia.infra;

import org.springframework.http.HttpStatus;

/* 
 * Classe record que será enviado como responsta das requisições (tanto em casos de erro, como de sucesso)
 * 
 * Atributos:
 *  status -> status HTTP da requisição.
 * 
 *  resp -> mensagem de erro em casos de falha ou "ok" em casos de sucesso
*/

public record ExceptionMessage(HttpStatus status, String resp) {

}
