package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/cadastroCnpj")
public class RegCnpjController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultasRepository consultaRepository;

    @GetMapping
    public String cadastroCnpj(@RequestParam("cnpj") String cnpj, Model model) {
        model.addAttribute("cnpj", cnpj);
        return "cadastroCnpj";
    }
    
    @PostMapping
    public String addEmpresa(@RequestParam("cnpj") String cnpj, @Valid RegCnpjDto dadosEmpresa) {
        


        Optional<UserModel> user = userRepository.findByCnpj(cnpj);

        if (user.isPresent()){
            var userEncontrado = user.get();
            EmpresaModel empresa = new EmpresaModel(dadosEmpresa.cnpjEmpresa(), dadosEmpresa.nome(), userEncontrado);
            empresaRepository.save(empresa);

            if (dadosEmpresa.checkboxSefaz().isPresent()){
                consultaRepository.save(new ConsultasModel(1, dadosEmpresa.frequenciaSefaz(), empresa));
            }

            if (dadosEmpresa.checkboxCndt().isPresent()){
                consultaRepository.save(new ConsultasModel(3, dadosEmpresa.frequenciaCndt(), empresa));
            }

        }
        
        return "redirect:/listaCnpj?cnpj=" + cnpj;
    }
    

}
