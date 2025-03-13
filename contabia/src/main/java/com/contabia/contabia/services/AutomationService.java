package com.contabia.contabia.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.dto.ListaNotasDto;
import com.contabia.contabia.models.dto.ListaRespostaDto;
import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.RespostaDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.*;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class AutomationService {

    @Autowired
    ConsultasRepository consultasRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    NotasRepository notasRepository;

    @Autowired
    RespostaRepository respostaRepository;
    
    public Map<Long, List<String>> getCnpjs(String frequencia, int tipoConsulta){

        Map<Long, List<String>> empresaCnpjsMap = new HashMap<>();
        
        List<Long> listIdEmpresa = getEmpresas( frequencia, tipoConsulta);
        
        for (Long id : listIdEmpresa) {
            Optional<EmpresaModel> empresa = empresaRepository.findById(id);
            if(empresa.isPresent()){
                Long key = empresa.get().getUser().getId();

                if(!empresaCnpjsMap.containsKey(key)){
                    empresaCnpjsMap.put(key, new ArrayList<>());
                } 

                empresaCnpjsMap.get(key).add(empresa.get().getCnpj());
            }
        }

        return empresaCnpjsMap;
    }

    public List<Long> getEmpresas(String frequencia, int tipoConsulta){

        List<Long> listIdEmpresa = new ArrayList<>();

        if (frequencia.equals("")) {
            listIdEmpresa = consultasRepository.getIdEmpresaByTipoConsulta(tipoConsulta);
        } else {
            listIdEmpresa = consultasRepository.findEmpresasByFrequenciAndTipoConsulta(frequencia, tipoConsulta);
        }

        return listIdEmpresa;
    }

    public void insereNotasBanco(ListaNotasDto listaNotas){

        for (NotasDto nota : listaNotas.listaNotas()) {

            Optional<EmpresaModel> empresa = empresaRepository.findByCnpj(nota.cnpjEmpresa());

            LocalDate diaAtual = LocalDate.now();
            
            NotasModel novaNota = new NotasModel();
        
            novaNota = new NotasModel(nota, true, diaAtual, empresa.get());

            if (notasRepository.findById(novaNota.getId()).isPresent()){
                continue;
            } else {
                notasRepository.save(novaNota);
            }
            
        }
    }

    public void insereRespostasBanco(ListaRespostaDto listaResp){

        for (RespostaDto resposta : listaResp.listaRespostas()) {

            Optional<ConsultasModel> consulta = consultasRepository.findConsultaByCnpjAndTipoConsulta(resposta.cnpjEmpresa(), 3);
            RespostaModel novaResposta = new RespostaModel(resposta, consulta.get());
            respostaRepository.save(novaResposta);
            
        }
    }

}