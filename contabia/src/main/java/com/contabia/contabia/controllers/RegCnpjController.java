package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.services.RegCnpjService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

    /*
     * Classe controller responsável por gereciar a página de cadastro de empresas e receber as informações para salvar as empresas e suas respectivas consultas no banco de dados.
     * 
     * rotas:
     *  /cadastroCnpj/ (GET) -> exibe a página de cadastro de empresa
     * 
     *  /cadastroCnpj/ (POST) -> recebe os dados que devem ser salvos na tabela "empresa" e "consulta" e realiza a persistência dos dados
    */

@Controller
@RequestMapping("/cadastroCnpj")
public class RegCnpjController {

    @Autowired
    private RegCnpjService regCnpjService;

    @GetMapping
    public String cadastroCnpj(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        model.addAttribute("cnpjUser", cnpjUser);//envio padrão do cnpj do usuário
        return "cadastroCnpj"; //exibe a página de cadastro de empresa
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> addEmpresa(@RequestParam("cnpjUser") String cnpjUser, @Valid @RequestBody RegCnpjDto dadosEmpresa) {
        return regCnpjService.cadEmpresa(cnpjUser, dadosEmpresa);
    }
}
