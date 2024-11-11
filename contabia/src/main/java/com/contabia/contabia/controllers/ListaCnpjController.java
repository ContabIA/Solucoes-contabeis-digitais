package com.contabia.contabia.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.EmpresaDto;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/listaCnpj")
public class ListaCnpjController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listaCnpj(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);
        List<EmpresaDto> infos = new ArrayList<>();

        if(user.isPresent()){
            var userEncontrado = user.get();
            List<EmpresaModel> empresas = empresaRepository.findByUser(userEncontrado);

            for (EmpresaModel empresaModel : empresas) {
                var nome = (empresaModel.getNome() + " - " + empresaModel.getCnpj());
                
                infos.add(0, new EmpresaDto(nome, empresaModel.getCnpj()));
            }
            model.addAttribute("empresasInfos", infos);
        }
        model.addAttribute("cnpjUser", cnpjUser);
        return "listaCnpj";
    }

    @DeleteMapping
    @Transactional
    public String delCnpj(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa){
        empresaRepository.deleteByCnpj(cnpjEmpresa);
        return "listaCnpj";
    }
}
