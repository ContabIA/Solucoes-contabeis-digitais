package com.contabia.contabia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.dto.EmpresaDto;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ListaCnpjService {

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    public List<EmpresaDto> criarLista(String cnpjUser){

        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);
        List<EmpresaDto> infos = new ArrayList<>(); //lista de informações que será enviado para o thymeleaf

        if(user.isPresent()){
            List<EmpresaModel> empresas = empresaRepository.findByUser(user.get()); //pesquisa todas as empresas do usuário atual

            //formatação do nome que será exibido na página e adição à lista
            for (EmpresaModel empresaModel : empresas) {
                var nome = (empresaModel.getNome() + " - " + empresaModel.getCnpj());
                
                infos.add(0, new EmpresaDto(nome, empresaModel.getCnpj()));
            }
        }

        return infos;
    }
}
