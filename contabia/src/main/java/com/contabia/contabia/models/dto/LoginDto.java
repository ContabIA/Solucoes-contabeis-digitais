package com.contabia.contabia.models.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(

    @NotBlank
    String cnpj,
    
    @NotBlank
    String senha
    
) {}
