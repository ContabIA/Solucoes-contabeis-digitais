package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.RespostaDto;
import com.contabia.contabia.services.DetailsAltService;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

/*
 * Classe controller responsável por gerenciar a página detalhes do site exibindo os detalhes de uma alteração no banco, podendo essa ser uma adição de uma nova nota ou uma respostas com status = 0.
 * 
 * Rotas:
 *  /detalhes/ (GET) -> Exibe a página detalhes e envia para o thymeleaf os detalhes da alteração que está sendo chamada
 *          /sefaz/ -> Exibe os detalhes das alterações na tabela notas
 *          /cndt/ - > Exibe os detalhes das alterações da tabela respostas
*/

@Controller
@RequestMapping("/detalhes")
public class DetailsAltController {

    @Autowired
    private DetailsAltService detailsAltService;
    
    @GetMapping("/sefaz")
    public String detalheSefaz(Authentication authentication, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @RequestParam("idAlt") Long idNota, Model model) {

        var cnpjUser = authentication.getName();

        model.addAttribute("cnpjUser", cnpjUser); // Envia para thymeleaf o cnpj do usário.
        model.addAttribute("tipoAlt", "Alteração Sefaz"); // Envia para o thymeleaf o tipo de Alteração
        model.addAttribute("cnpjEmpresa", cnpjEmpresa); // Envia para o thymeleaf o cnpj da empresa que a alteração está associada.
        
        NotasDto notaDto = detailsAltService.getNota(idNota, cnpjEmpresa);  // Funcao que retorna DTO da nota

        model.addAttribute("nota", notaDto); //Envia para o thymeleaf o objeto de transferência de dados da nota que deve ser exibido os detalhes.
        model.addAttribute("mes", notaDto.data().getMonth()); // Envia para o thymeleaf o mês da nota.
        model.addAttribute("idAlt", idNota); // Envia para o thymeleaf o id da nota.

        return "detalhes";
    }

    @GetMapping("/cndt")
    public String detalheCndt(Authentication authentication, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @RequestParam("idAlt") Long idResp, Model model){

        var cnpjUser = authentication.getName();

        model.addAttribute("cnpjUser", cnpjUser); // Envia para thymeleaf o cnpj do usário.
        model.addAttribute("tipoAlt", "Alteração Sefaz"); // Envia para o thymeleaf o tipo de Alteração
        model.addAttribute("cnpjEmpresa", cnpjEmpresa); // Envia para o thymeleaf o cnpj da empresa que a alteração está associada.

        RespostaDto respostaDto = detailsAltService.getResposta(idResp, cnpjEmpresa); // Função que retorna o DTO da resposta

        model.addAttribute("resp", respostaDto); //Envia para o thymeleaf o objeto de transferência de dados da resposta que deve ser exibido os detalhes.
        model.addAttribute("mes", respostaDto.data().getMonth()); // Envia para o thymeleaf o mês da resposta.
        model.addAttribute("idAlt", idResp); // Envia para o thymeleaf o id da resposta.

        return "detalhes";
    }
    
}
