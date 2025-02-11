package com.contabia.contabia.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.RespostaDto;
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

@Controller
@RequestMapping("/service")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    @GetMapping("/getCnpj")
    public ResponseEntity<List<String>> getCnpj(@RequestParam("ultimoDigito") int ultimoDigito, @RequestParam("tamanhoFinal") int tamanhoFinal, @RequestParam("frequencia") String frequencia, @RequestParam("tipoConsulta") int tipoConsulta) {

        List<String> cnpjs = automationService.getCnpjsDia(ultimoDigito, frequencia, tipoConsulta, tamanhoFinal); // Lista com os cnpjs da empresa
        
        return ResponseEntity.ok().body(cnpjs); // Retorno da requisição com lista de cnps's como body.
    } 

    @GetMapping("/getDadosLogin")
    public ResponseEntity<List<String>> getDadosLogin(@RequestParam("cnpjEmpresa") String cnpjEmpresa){
        
        return automationService.getDadosLogin(cnpjEmpresa);
        
    }

    @PostMapping("/respSefaz")
    public ResponseEntity<String> respSefaz(@RequestBody ArrayList<NotasDto> listaNotas) {
        
        automationService.insereNotasBanco(listaNotas); // Método que insere notas no banco
        
        return ResponseEntity.ok().body("Notas enviadas com sucesso!");
    }

    @PostMapping("/respCndt")
    public ResponseEntity<String> respCndt(@RequestBody ArrayList<RespostaDto> listaResp) {
        
        automationService.insereRespostasBanco(listaResp); // Método que insere respostas no banco
        
        return ResponseEntity.ok().body("Respostas enviadas com sucesso!");
    }

    @PostMapping("/consultaManual")
    public String consultaManual(Authentication authentication) {
        
        String cnpjUser = authentication.getName();

        List<String> cnpjsSefaz = automationService.getAllCnpjsSefaz(cnpjUser);

        automationService.requisicaoConsultaManual(cnpjsSefaz);
        
        return "redirect:/home";
    }

}
