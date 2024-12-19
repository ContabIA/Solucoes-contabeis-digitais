package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.services.EditCnpjService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

    /*
     * Classe controller responsável por gerenciar a página de edição de uma empresa cadastrada e alteração das informações
     * no banco de dados.
     * 
     * rotas:
     *  /editCnpj/ (GET) -> exibe a página de edição e envia para o thymeleaf as informações atuais que devem ser exibidas.
     *  
     *  /editCnpj/ (PUT) -> recebe os dados da página de edição e atualiza os dados já existentes e cria novas consultas caso 
     *  necessário. Retorna o cnpj do usuário.
    */

@Controller
@RequestMapping("/editCnpj")
public class EditCnpjController {

    @Autowired
    private EditCnpjService editCnpjService;

    @GetMapping
    public String editCnpj(Authentication authentication, @RequestParam("cnpjEmpresa") String cnpjEmpresa, Model model) {
        var cnpjUser = authentication.getName();

        model.addAttribute("cnpjUser", cnpjUser); //envia para o thymeleaf o cnpj do usuário
        model.addAttribute("cnpjEmpresa", cnpjEmpresa); //envia para o thymeleaf o cnpj da empresa editada

        return editCnpjService.enviarDadosAtuais(cnpjUser, cnpjEmpresa, model);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> criarEdicao(Authentication authentication, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @Valid @RequestBody RegCnpjDto dadosEmpresa) {
        var cnpjUser = authentication.getName();

        editCnpjService.atualizar(dadosEmpresa, cnpjEmpresa);
        return ResponseEntity.ok().body(cnpjUser); //retorna para o cnpj do usuário para o JS
    }
}