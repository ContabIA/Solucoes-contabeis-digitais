package com.contabia.contabia.models.dto;

import java.util.List;
import java.util.Map;

/*
 * Classe record (DTO) responsável por fazer a transferência da lista de CNPJs que serão consultados pelas automações.
 * 
 * cnpjs -> lista de CNPJs
*/

public record AutomationDto(

    Map<String, String> login,

    List<String> cnpjs
    
){}
