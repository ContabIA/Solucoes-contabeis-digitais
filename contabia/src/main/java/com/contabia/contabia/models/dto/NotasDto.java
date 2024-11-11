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
<<<<<<< HEAD
    String valor,
=======
    double valor,
>>>>>>> 316fd6ae9325e80a2e5c51f880f663308bded6a3

    @NotBlank
    String cnpjEmpresa

) {}
