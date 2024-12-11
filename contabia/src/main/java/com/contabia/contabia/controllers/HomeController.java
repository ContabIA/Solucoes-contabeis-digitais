package com.contabia.contabia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.AltDto;
import com.contabia.contabia.services.HomeService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

/*
 * Classe controller responsável por gerenciar a página principal do site exibindo as alterações detectadas de um usuário.
 * 
 * Rotas:
 *  /home/ (GET) -> Exibe a página principal e envia para o thymeleaf as informações atuais que devem ser exibidas.
 *  
 *  /editCnpj/ (PUT) -> Recebe os dados da alteração que foi vizualizada na página detalhes e faz uma exclusão lógica dessa alteração.
*/

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping
    public String home(Model model, Authentication authentication) {

        var cnpjUser = authentication.getName();

        model.addAttribute("cnpjUser", cnpjUser); //Envia para o thymeleaf o cnpj do usuário. 

        List<AltDto> infos = homeService.getListaAlt(cnpjUser); // Função que retorna todas as alterações relacionados ao usuário

        model.addAttribute("infosAlt", infos); //Envia para o thymeleaf as alterações que devem ser expostas
        return "home";
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> editNovo(@RequestParam("tipoAlt") String tipoAlt, @RequestParam("idAlt") Long idAlt, Authentication authentication) {
        homeService.excluiAlteracao(idAlt, tipoAlt); // Função que remove a alteração da tela inicial após ser vista
        
        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok")); // Retorna o cnpj do usuário para o JS para que ele faça a requisição para o GET da rota home.
    }
}