package com.contabia.contabia.models.dto;

/*
 * Classe record (DTO) responsável por fazer a transferência dos dados das empresas para o thymeleaf.
 * 
 * atributos:
 *  nome -> nome que será exibido para o usuário.
 * 
 *  cnpjEmpresa -> CNPJ da empresa em questão.
*/

public record EmpresaDto(String nome, String cnpj) {

}
