package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.NotBlank;

public record RespostaDto(

    @NotBlank
    byte status,

    @NotBlank
    boolean novo,

    @NotBlank
    String cnpjEmpresa

) {}
