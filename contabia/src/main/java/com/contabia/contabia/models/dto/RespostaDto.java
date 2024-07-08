package com.contabia.contabia.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record RespostaDto(

    @NotBlank
    byte status,

    @NotBlank
    LocalDate data,

    @NotBlank
    boolean novo,

    @NotBlank
    String cnpjEmpresa

) {}
