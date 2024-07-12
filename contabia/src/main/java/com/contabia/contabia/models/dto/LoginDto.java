package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe record (DTO) responsável por fazer a transferência dos dados de login do usuário.
 * 
 * atributos:
 *  cnpj -> CNPJ digitado pelo usuário no campo de login.
 * 
 *  senha -> senha digitada pelo usuário no campo de login.
*/

public record LoginDto(

    @NotBlank
    String cnpj,
    
    @NotBlank
    String senha
    
) {}
