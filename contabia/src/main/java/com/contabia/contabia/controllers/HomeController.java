package com.contabia.contabia.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.AltDto;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.NotasRepository;
import com.contabia.contabia.repository.RespostaRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
    private NotasRepository notasRepository;


    @Autowired
    private RespostaRepository respostaRepository;

    @GetMapping
    public String home(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        model.addAttribute("cnpjUser", cnpjUser);
        

        Optional<List<NotasModel>> optionalNotas = notasRepository.findByNovoAndCnpjUser(true, cnpjUser);
        Optional<List<RespostaModel>> optionalResposta = respostaRepository.findByNovoAndCnpjUserAndStatus(true, cnpjUser, 0);
        List<AltDto> infos = new ArrayList<>();

        if (optionalNotas.isPresent()){
            for (NotasModel nota : optionalNotas.get()) {
                infos.add(new AltDto("Alteração Sefaz - " +  nota.getEmpresaNotas().getCnpj() + " - " + nota.getData().getMonth(), nota.getEmpresaNotas().getCnpj(), nota.getId(), "sefaz"));
            }
        }

        if(optionalResposta.isPresent()){
            for (RespostaModel resposta : optionalResposta.get()) {
                infos.add(new AltDto("Alteração CNDT - " + resposta.getConsulta().getEmpresaConsulta().getCnpj() + " - " + resposta.getData().getMonth(), resposta.getConsulta().getEmpresaConsulta().getCnpj(), resposta.getId(), "cndt"));
            }
        }
        model.addAttribute("infosAlt", infos);
        return "home";
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> editNovo(@RequestParam("tipoAlt") String tipoAlt, @RequestParam("idAlt") Long idAlt, @RequestParam("cnpjUser") String cnpjUser) {
        
        
        if (tipoAlt.equals("Alteração Sefaz")){
            Optional<NotasModel> optionalNota = notasRepository.findById(idAlt);
            if (optionalNota.isPresent()){
                NotasModel nota = optionalNota.get();
                nota.setNovo(false);
                notasRepository.save(nota);
            }
            
        } else {
            Optional<RespostaModel> optionalResposta = respostaRepository.findById(idAlt);
            if (optionalResposta.isPresent()){
                RespostaModel resposta = optionalResposta.get();
                resposta.setNovo(false);
                respostaRepository.save(resposta);
            }
            
        }
        
        return ResponseEntity.ok().body(cnpjUser);
    }
}
