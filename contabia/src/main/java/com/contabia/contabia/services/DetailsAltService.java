package com.contabia.contabia.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.dto.ListaNotasDto;
import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.RespostaDto;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.NotasRepository;
import com.contabia.contabia.repository.RespostaRepository;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
public class DetailsAltService {

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private RespostaRepository respostaRepository;
    
    public NotasDto getNota(Long idNota){

        // Busca no banco a nota que deve ser mostrado os detalhes e cria o objeto de transferência de dados.
        NotasModel nota = notasRepository.findById(idNota).get();
        NotasDto notaDto = new NotasDto(idNota, nota.getData(), nota.getSerie(), nota.getNomeEmitente(), nota.getSituacao(), nota.getValor(), nota.getEmpresaNotas().getCnpj());

        return notaDto; // Retorna o DTO da nota.
    }

    public RespostaDto getResposta(Long idResp, String cnpjEmpresa){

        // Busca no banco a resposta que deve ser mostrado os detalhes e cria o objeto de transferência de dados.
        RespostaModel resp = respostaRepository.findById(idResp).get();
        RespostaDto respostaDto = new RespostaDto(resp.getStatus(), resp.getData(), resp.getNovo(), cnpjEmpresa);
        
        return respostaDto; // Retorna o DTO da resposta.
    }

    public ListaNotasDto detalhesEncaps(String mes, String cnpjEmpresa){
        LocalDate dataAtual = LocalDate.now();

        Map<String, Integer> mapMes = new HashMap<String, Integer>(){{
            put("JANUARY", 1);
            put("FEBRUARY", 2);
            put("MARCH", 3);
            put("APRIL", 4);
            put("MAY",5);
            put("JUNE", 6);
            put("JULY", 7);
            put("AUGUST", 8);
            put("SEPTEMBER", 9);
            put("OCTOBER", 10);
            put("NOVEMBER", 11);
            put("DECEMBER", 12);
        }};
        
        LocalDate data = LocalDate.of(dataAtual.getYear(), mapMes.get(mes) + 1, 01);

        if (data.isAfter(dataAtual)){
            data = LocalDate.of(dataAtual.getYear()-1, mapMes.get(mes) + 1, 01);
        }

        Optional<List<NotasModel>> OptionalListaNotas= notasRepository.findNotasEncapsByDataInsercaoAndCnpjEmpresa(data, cnpjEmpresa);

        List<NotasDto> Notas = new ArrayList<>();

        if (OptionalListaNotas.isPresent()){
            for (NotasModel nota : OptionalListaNotas.get()) {
                if (nota.getData().getMonth().toString().equals(mes)){
                    Notas.add(new NotasDto(nota.getId(), nota.getData(), nota.getSerie(), nota.getNomeEmitente(), nota.getSituacao(), nota.getValor(), cnpjEmpresa));
                }
            }
            
        }

        return new ListaNotasDto(Notas);
    }

}
