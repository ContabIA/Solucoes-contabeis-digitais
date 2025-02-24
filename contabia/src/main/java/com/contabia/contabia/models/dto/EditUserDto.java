package com.contabia.contabia.models.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EditUserDto(

    @CNPJ(message="CNPJ inválido")
    @NotBlank
    String cnpj,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String senhaSefaz,

    @NotBlank
    String userSefaz
) {

}
