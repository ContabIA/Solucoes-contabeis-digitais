package com.contabia.contabia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public NotasDto getNota(Long idNota, String cnpjEmpresa){

        // Busca no banco a nota que deve ser mostrado os detalhes e cria o objeto de transferência de dados.
        NotasModel nota = notasRepository.findById(idNota).get();
        NotasDto notaDto = new NotasDto(idNota, nota.getData(), nota.getSerie(), nota.getNomeEmitente(), nota.getSituacao(), nota.getValor(), cnpjEmpresa);

        return notaDto; // Retorna o DTO da nota.
    }

    public RespostaDto getResposta(Long idResp, String cnpjEmpresa){

        // Busca no banco a resposta que deve ser mostrado os detalhes e cria o objeto de transferência de dados.
        RespostaModel resp = respostaRepository.findById(idResp).get();
        RespostaDto respostaDto = new RespostaDto(resp.getStatus(), resp.getData(), resp.getNovo(), cnpjEmpresa);
        
        return respostaDto; // Retorna o DTO da resposta.
    }

}
