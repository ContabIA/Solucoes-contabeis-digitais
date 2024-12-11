package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.services.ListaCnpjService;

import jakarta.transaction.Transactional;

    /*
     * Classe controller responsável por gerenciar a página que exibe a lista de empresas cadastradas pelo usuário.
     * 
     * rotas:
     *  /listaCnpj/ (GET) -> exibe a página de listagem de empresas cadastradas e envia para o thymeleaf uma lista das empresas cadastradas pelo usuário atual.
     * 
     *  /listaCnpj/ (DELETE) -> recebe o CNPJ da empresa que será deletada e realiza a exclusão no banco de dados.
    */

@Controller
@RequestMapping("/listaCnpj")
public class ListaCnpjController {

    @Autowired
    private ListaCnpjService listaCnpjService;

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    @GetMapping
    public String listaCnpj(Authentication authentication, Model model) {
        var cnpjUser = authentication.getName();
        
        var infos = listaCnpjService.criarLista(cnpjUser);

        model.addAttribute("empresasInfos", infos); //envia para o thymeleaf a lista de informações
        model.addAttribute("cnpjUser", cnpjUser); //envio padrão do cnpj do usuário
        return "listaCnpj";
    }

    @DeleteMapping
    @Transactional
    public String delCnpj(@RequestParam("cnpjEmpresa") String cnpjEmpresa){
        //recebe o cnpj do usuário e da empresa que será deletada como parâmetro de requisição e realiza a exclusão
        empresaRepository.deleteByCnpj(cnpjEmpresa);
        return "listaCnpj"; //exibe novamente a mesma página atualizada
    }
}
