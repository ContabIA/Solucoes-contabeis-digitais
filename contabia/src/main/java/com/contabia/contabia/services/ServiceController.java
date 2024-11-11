package com.contabia.contabia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contabia.contabia.models.dto.ListaNotasDto;
import com.contabia.contabia.models.dto.ListaRespostaDto;
import com.contabia.contabia.models.dto.RespostaDto;
import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.ServiceDto;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.RespostaRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.NotasRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @GetMapping("/getCnpj")
    public ResponseEntity<ServiceDto> getCnpj(@RequestParam("tamanhoFinal") int tamanhoFinal, @RequestParam("ultimoDigito") int ultimoDigito, @RequestParam("frequencia") int frequencia, @RequestParam("tipoConsulta") int tipoConsulta) {

        List<Long> listIdEmpresa = consultasRepository.empresasOfDay(tamanhoFinal, ultimoDigito, frequencia, tipoConsulta);
        List<String> cnpjs = new ArrayList<>();

        for (Long id : listIdEmpresa) {
            Optional<EmpresaModel> empresa = empresaRepository.findById(id);

            if(empresa.isPresent()){
                cnpjs.add(empresa.get().getCnpj());
            }
        }

        return ResponseEntity.ok().body(new ServiceDto(cnpjs));
    } 

    @PostMapping("/respSefaz")
    public ResponseEntity<String> respSefaz(@RequestBody ListaNotasDto listaNotas) {
        
        for (NotasDto nota : listaNotas.listaNotas()) {

            Optional<EmpresaModel> empresa = empresaRepository.findByCnpj(nota.cnpjEmpresa());
            NotasModel novaNota = new NotasModel(nota, true, empresa.get());
            
            try {

                notasRepository.save(novaNota);
                
            } catch (Exception e) {
                continue;
            }
        }
        return ResponseEntity.ok().body("deu certo!");
    }

    @PostMapping("/respCndt")
    public ResponseEntity<String> respCndt(@RequestBody ListaRespostaDto listaResp) {
        
        for (RespostaDto resposta : listaResp.listaRespostas()) {

            Optional<ConsultasModel> consulta = consultasRepository.findConsultaByCnpjAndTipoConsulta(resposta.cnpjEmpresa(), 3);
            RespostaModel novaResposta = new RespostaModel(resposta, consulta.get());
            respostaRepository.save(novaResposta);
            
        }
        return ResponseEntity.ok().body("deu certo!");
    }

}
