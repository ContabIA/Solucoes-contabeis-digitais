package com.contabia.contabia.models.dto;

import java.util.List;

/*
 * Classe record (DTO) responsável por fazer a transferência de uma lista de outros DTOs de notas por meio de requisição HTTP.
 * 
 * atributos:
 *  listaNotas -> lista de NotasDto.
*/

public record ListaNotasDto(

    List<NotasDto> listaNotas

) {}
