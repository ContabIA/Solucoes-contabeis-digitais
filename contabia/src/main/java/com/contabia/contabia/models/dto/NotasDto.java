package com.contabia.contabia.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

//import jakarta.validation.constraints.NotBlank;

public record NotasDto(

    @NotBlank
    Long id,

    @NotBlank
    LocalDate data,

    @NotBlank
    int serie,

    @NotBlank
    String nomeEmitente,

    @NotBlank
    String situacao,

    @NotBlank
    String valor,

    @NotBlank
    String cnpjEmpresa

) {}
