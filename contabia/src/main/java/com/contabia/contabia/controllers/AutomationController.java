package com.contabia.contabia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contabia.contabia.models.dto.AutomationDto;
import com.contabia.contabia.models.dto.DadosLoginDto;
import com.contabia.contabia.models.dto.ListaNotasDto;
import com.contabia.contabia.models.dto.ListaRespostaDto;
import com.contabia.contabia.services.AutomationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Classe rest controller responsável por receber e gerenciar requisições de aplicações externas e criar métodos auxiliares. 
 * 
 * Rotas:
 *  /service/ (GET) -> Rotas para aplicações externas receberem informações para executar as automações.
 * 
 *          /getCnpj/ -> Envia para a aplicação que requisitar todos os cnpj's das consultas que devem ser feitas naquele dia, de acordo com os paramêtros que vão ser mandandos na requisição.
 *          /getDadosLogin/ -> Envia para a aplicação que requisitar os dados do login no site do Sefaz para o cnpj que for enviado como parâmetro.
 *  
 *  /service/ (PUT) -> Recebe a resposta de automações executadas na aplicação externa com dados que devem ser incluidos no banco de dados.
 * 
 *          /respSefaz/ -> Recebe uma lista de notas que devem ser incluidas no banco.
 *          /respCndt/ -> Recebe uma lista de respostas que devem ser incluidas no banco.
*/

@RestController
@RequestMapping("/service")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    @GetMapping("/getCnpj")
    public ResponseEntity<AutomationDto> getCnpj(@RequestParam("ultimoDigito") int ultimoDigito, @RequestParam("tamanhoFinal") int tamanhoFinal, @RequestParam("frequencia") String frequencia, @RequestParam("tipoConsulta") int tipoConsulta) {

        List<String> cnpjs = automationService.getCnpjsDia(ultimoDigito, frequencia, tipoConsulta, tamanhoFinal); // Lista com os cnpjs da empresa
        
        return ResponseEntity.ok().body(new AutomationDto(cnpjs)); // Retorno da requisição com lista de cnps's como body.
    } 

    @GetMapping("/getDadosLogin")
    public ResponseEntity<DadosLoginDto> getDadosLogin(@RequestParam("cnpjEmpresa") String cnpjEmpresa){
        
        return automationService.getDadosLogin(cnpjEmpresa);
        
    }

    @PostMapping("/respSefaz")
    public ResponseEntity<String> respSefaz(@RequestBody ListaNotasDto listaNotas) {
        
        automationService.insereNotasBanco(listaNotas); // Método que insere notas no banco
        
        return ResponseEntity.ok().body("Notas enviadas com sucesso!");
    }

    @PostMapping("/respCndt")
    public ResponseEntity<String> respCndt(@RequestBody ListaRespostaDto listaResp) {
        
        automationService.insereRespostasBanco(listaResp); // Método que insere respostas no banco
        
        return ResponseEntity.ok().body("Respostas enviadas com sucesso!");
    }

}
