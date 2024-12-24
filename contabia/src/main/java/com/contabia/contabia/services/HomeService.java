package com.contabia.contabia.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.dto.AltDto;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.NotasRepository;
import com.contabia.contabia.repository.RespostaRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class HomeService {

    @Autowired
    NotasRepository notasRepository;

    @Autowired
    RespostaRepository respostaRepository;
    
    public Map<String, List<AltDto>> getListaAlt(String cnpjUser){
        
        List<AltDto> infos = new ArrayList<>(); // Lista para inserir as alterações

        //infos.addAll(getNotas(cnpjUser)); // Adiciona à lista as notas novas
        infos.addAll(getRespostas(cnpjUser)); // Adiciona à lista respostas novas
        
        Map<String, List<AltDto>> dictNotas = getNotas(cnpjUser);
        dictNotas.put("cndt", infos);

        return dictNotas;

    }

    public Map<String, List<AltDto>> getNotas(String cnpjUser){

        Map<String, List<AltDto>> dictNotas = new HashMap<>();

        // Coleta lista com notas que devem ser expostas na tela.
        Optional<List<NotasModel>> optionalNotas = notasRepository.findByNovoAndCnpjUser(true, cnpjUser);

        // Verifica se há conteudo nas listas com as notas
        if (optionalNotas.isPresent()){
            //Cria o objeto de transferencia de dados e adiciona na lista infos cada alteração com o que deve ser exposto na tela
            for (NotasModel nota : optionalNotas.get()) {

                if (nota.getDataInsercao().getDayOfMonth() == 1){
                    String key = nota.getData().getMonth().toString() + "-" + nota.getEmpresaNotas().getCnpj();

                    if ( ! (dictNotas.containsKey(key))){
                        
                        dictNotas.put(key, new ArrayList<>());
                    } 
                    dictNotas.get(key).add(new AltDto(nota.getData().getMonth().toString(), nota.getEmpresaNotas().getCnpj(), nota.getId(), "sefaz"));
                
                } else {
                    if (! dictNotas.containsKey("sefaz")){
                        dictNotas.put("sefaz", new ArrayList<>());
                    }
                    dictNotas.get("sefaz").add(new AltDto(nota.getData().getMonth().toString(), nota.getEmpresaNotas().getCnpj(), nota.getId(), "sefaz"));
                }

            }
        }

        return dictNotas; // Retorna as notas novas

    }

    public List<AltDto> getRespostas(String cnpjUser){

        List<AltDto> listaAlt = new ArrayList<>();// Lista para receber respostas novas

        //Coleta lista com respostas que devem ser expostas.
        Optional<List<RespostaModel>> optionalResposta = respostaRepository.findByNovoAndCnpjUserAndStatus(true, cnpjUser, 0);

        // Verifica se há conteudo nas listas com as respostas
        if(optionalResposta.isPresent()){
            //Adiciona na lista infos cada alteração com o que deve ser exposto na tela
            for (RespostaModel resposta : optionalResposta.get()) {
                listaAlt.add(new AltDto(resposta.getData().getMonth().toString(), resposta.getConsulta().getEmpresaConsulta().getCnpj(), resposta.getId(), "cndt"));
            }
        }

        return listaAlt; // Retorna as respostas novas
    }

    public void excluiAlteracao(Long idAlt, String tipoAlt){

        //Verifica o tipo de alteração que foi vizualizada na página detalhes.
        if (tipoAlt.equals("Alteração Sefaz")){
            //Busca no banco a nota que foi vizualizada e muda o atributo novo para 0, o que indica que ela já foi vizualizada
            Optional<NotasModel> optionalNota = notasRepository.findById(idAlt);
            if (optionalNota.isPresent()){
                NotasModel nota = optionalNota.get();
                nota.setNovo(false);
                notasRepository.save(nota);
            }
            
        } else {
            //Busca no banco a resposta que foi vizualizada e muda o atributo novo para 0, o que indica que ela já foi vizualizada
            Optional<RespostaModel> optionalResposta = respostaRepository.findById(idAlt);
            if (optionalResposta.isPresent()){
                RespostaModel resposta = optionalResposta.get();
                resposta.setNovo(false);
                respostaRepository.save(resposta);
            }
            
        }

    }

}
