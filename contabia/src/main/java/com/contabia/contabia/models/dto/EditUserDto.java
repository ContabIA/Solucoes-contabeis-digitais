package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EditUserDto(
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
