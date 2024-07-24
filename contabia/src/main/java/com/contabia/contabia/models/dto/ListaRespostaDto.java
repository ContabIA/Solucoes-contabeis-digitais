package com.contabia.contabia.models.dto;

import java.util.List;

/*
 * Classe record (DTO) responsável por fazer a transferência de uma lista de outros DTOs de respostas (CNDT) por meio de requisição HTTP.
 * 
 * atributos:
 *  listaRespostas -> lista de RespostaDto.
*/

public record ListaRespostaDto(

    List<RespostaDto> listaRespostas

) {}
