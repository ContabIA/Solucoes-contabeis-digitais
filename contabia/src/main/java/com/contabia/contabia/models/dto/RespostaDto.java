package com.contabia.contabia.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe record (DTO) responsável por fazer a transferência das informações retornadas de uma consulta CNDT ao banco de dados.
 * 
 * atributos:
 *  status -> status da consulta (se foi bem ou se houve algum erro).
 * 
 *  data -> data da realização da consulta.
 * 
 *  novo -> valor que define se esta consulta já foi visualizada ou não.
 * 
 *  cnpjEmpresa -> CNPJ da empresa que foi consultada
*/

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
