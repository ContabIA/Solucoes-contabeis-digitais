package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * Classe record (DTO) responsável por fazer a transferência das informações de um novo cadastro de usuário.
 * 
 * atributos:
 *  cnpj -> CNPJ do usuário.
 * 
 *  email -> e-mail do usuário.
 * 
 *  senha -> senha do login do usuário em sua conta da ContabIA.
 * 
 *  senhaSefaz -> senha da conta Sefaz do usuário (necessário para a consulta de possíveis pendências).
 * 
 *  userSefaz -> username da conta Sefaz do usuário (necessário para a consulta de possíveis pendências).
*/

public record UserDto(
    @NotBlank
    String cnpj,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String senha,

    @NotBlank
    String senhaSefaz,

    @NotBlank
    String userSefaz
) {}
