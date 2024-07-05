package com.contabia.contabia.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;


@Controller
@RequestMapping("/editCnpj")
public class EditCnpjController {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String editCnpj(@RequestParam("cnpj") String cnpj, @RequestParam("cnpjEmpresa") String cnpjEmpresa, Model model) {
        Optional<UserModel> user = userRepository.findByCnpj(cnpj);

        if(user.isPresent()){
            var userEncontrado = user.get();

            EmpresaModel empresa = empresaRepository.findByUserAndCnpj(userEncontrado, cnpjEmpresa);
            
            model.addAttribute("nomeEmpresa", empresa.getNome());
            model.addAttribute("cnpjEmpresa", cnpjEmpresa);

            List<ConsultasModel> consultas = consultasRepository.findByEmpresaConsulta(empresa);

            for (ConsultasModel consultasModel : consultas) {                
                if(consultasModel.getTipoConsulta() == 1) model.addAttribute("sefazStatus", true);
                if(consultasModel.getTipoConsulta() == 3) model.addAttribute("cndtStatus", true);
            }

            Map<Integer, String> sla = new HashMap<>();
            sla.put(1, "freqSefaz");
            sla.put(3, "freqCndt");

            //feito por arnaud
            for(int i = 0; i < consultas.size(); i++){
                model.addAttribute(sla.get(consultas.get(i).getTipoConsulta()) + Integer.toString(consultas.get(i).getFrequencia()), true);
            }
        }
        model.addAttribute("cnpj", cnpj);
        return "editCnpj";
    }
    
}
