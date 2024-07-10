package com.contabia.contabia.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


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
    public String editCnpj(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, Model model) {
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);

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
        model.addAttribute("cnpjUser", cnpjUser);
        model.addAttribute("cnpjEmpresa", cnpjEmpresa);        
        return "editCnpj";
    }
    

    @PutMapping
    @Transactional
    public ResponseEntity<String> criarEdicao(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @Valid @RequestBody RegCnpjDto dadosEmpresa) {

        Optional<EmpresaModel> empresa =  empresaRepository.findByCnpj(cnpjEmpresa);
        var empresaEncontrada = empresa.get();

        
        Optional<ConsultasModel> optionalConsultaSefaz = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 1);
        Optional<ConsultasModel> optionalConsultaCndt = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 3);

        if(dadosEmpresa.checkboxSefaz().get() == true){
            if(optionalConsultaSefaz.isPresent()){
                var consultaSefaz = optionalConsultaSefaz.get();
                consultaSefaz.editConsulta(dadosEmpresa.frequenciaSefaz());
                consultasRepository.save(consultaSefaz);
            }else{
                consultasRepository.save(new ConsultasModel(1, dadosEmpresa.frequenciaSefaz(), empresaEncontrada));
            }
        }else{
            if(optionalConsultaSefaz.isPresent()){  
                consultasRepository.deleteById(optionalConsultaSefaz.get().getId());
            }
        }

        if(dadosEmpresa.checkboxCndt().get() == true){
            if(optionalConsultaCndt.isPresent()){
                var consultaCndt = optionalConsultaCndt.get();
                consultaCndt.editConsulta(dadosEmpresa.frequenciaCndt());
                consultasRepository.save(consultaCndt);
            }else{
                consultasRepository.save(new ConsultasModel(3, dadosEmpresa.frequenciaCndt(), empresaEncontrada));
            }
        }else{
            if(optionalConsultaCndt.isPresent()){
                consultasRepository.deleteById(optionalConsultaCndt.get().getId());
            }
        }

        empresaEncontrada.editEmpresa(dadosEmpresa.cnpjEmpresa(), dadosEmpresa.nome());
        empresaRepository.save(empresaEncontrada);


        return ResponseEntity.ok().body(cnpjUser);
    }
}
