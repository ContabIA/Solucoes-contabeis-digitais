package com.contabia.contabia.models.dto;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;

public record RegCnpjDto(
    
    @NotBlank
    String cnpjEmpresa,

    @NotBlank
    String nome,

    Optional<Boolean> checkboxSefaz,

    Optional<Boolean> checkboxCndt,

    int frequenciaSefaz,

    int frequenciaCndt

) {}
