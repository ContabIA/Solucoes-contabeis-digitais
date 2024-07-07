package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.repository.NotasRepository;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/detalhes")
public class DetailsAltController {

    @Autowired
    private NotasRepository notasRepository;
    
    @GetMapping("/sefaz")
    public String detalheSefaz(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @RequestParam("idAlt") Long idNota, Model model) {

        model.addAttribute("cnpjUser", cnpjUser);
        model.addAttribute("tipoAlt", "Alteração Sefaz");
        NotasModel nota = notasRepository.findById(idNota).get();
        NotasDto notaDto = new NotasDto(idNota, nota.getData(), nota.getSerie(), nota.getNomeEmitente(), nota.getSituacao(), nota.getValor(), cnpjEmpresa);
        model.addAttribute("nota", notaDto);


        return "detalhes";

    }

    @GetMapping("/cndt")
    public String detalheCndt(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @RequestParam("idAlt") Long idNota, Model model){

        model.addAttribute("cnpjUser", cnpjUser);
        model.addAttribute("tipoAlt", "Alteração Cndt");

        
        return "detalhes";
    }
    
}
