package com.contabia.contabia.models.dto;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe record (DTO) responsável por fazer a transferência dos dados necessários para adicionar uma empresa ao banco de dados, juntamente com informações sobre suas consultas (sefaz e CNDT).
 * 
 * atributos:
 *  cnpjEmpresa -> CNPJ da empresa que está sendo criada.
 * 
 *  nome -> nome da empresa que está sendo criada.
 * 
 *  checkboxSefaz -> valor que identifica se foi marcada uma consulta sefaz.
 * 
 *  checkboxCndt -> valor que identifica se foi marcada uma consulta CNDT.
 * 
 *  frequenciaSefaz -> caso tenha sido marcado uma consulta sefaz, este valor é utilizado para definir o período que ela deve ser realizada.
 * 
 *  frequenciaCndt -> caso tenha sido marcado uma consulta CNDT, este valor é utilizado para definir o período que ela deve ser realizada.
*/

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
