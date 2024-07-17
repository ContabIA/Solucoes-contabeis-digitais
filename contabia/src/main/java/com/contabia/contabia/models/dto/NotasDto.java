package com.contabia.contabia.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe record (DTO) responsável por fazer a transferência das informações necessárias para salvar uma nota no banco de dados.
 * 
 * atributos:
 *  id -> identificador da nota.
 * 
 *  data -> data de emissão da nota.
 * 
 *  serie -> código de série da nota.
 * 
 *  nomeEmitente -> nome do emitente da nota.
 * 
 *  situacao -> status de situação da nota (do momento da consulta).
 * 
 *  valor -> valor total da nota.
 * 
 *  cnpjEmpresa -> CNPJ da empresa que possui a determinada nota
*/

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
