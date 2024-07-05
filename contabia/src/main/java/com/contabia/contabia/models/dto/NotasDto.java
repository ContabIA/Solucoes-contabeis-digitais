package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.NotBlank;

//import jakarta.validation.constraints.NotBlank;

public record NotasDto(

    @NotBlank
    Long id,

    @NotBlank
    String data,

    @NotBlank
    int serie,

    @NotBlank
    String nomeEmitente,

    @NotBlank
    int situacao,

    @NotBlank
    double valor,

    @NotBlank
    String cnpjEmpresa

) {}
