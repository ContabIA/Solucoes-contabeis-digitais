package com.contabia.contabia.models.dto;

/*
 * Classe record (DTO) responsável por fazer a transferência dos dados das alterações exibidas para HomeController.
 * 
 * atributos:
 *  texto -> texto que será exibido para o usuário em cada alteração.
 * 
 *  cnpjEmpresa -> cnpj da empresa que está relacionada a alteração.
 * 
 *  altId -> identificador da alteração (nota ou resposta) 
 * 
 *  tipoAlt -> tipo da alteração (sefaz ou CNDT)
*/

public record AltDto(String texto, String cnpjEmpresa, Long altId, String tipoAlt) {}
