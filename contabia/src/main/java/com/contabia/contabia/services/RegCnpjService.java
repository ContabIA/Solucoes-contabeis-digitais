package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RegCnpjService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultasRepository consultaRepository;
    
    public ResponseEntity<ExceptionMessage> registerCnpj(String cnpjUser, RegCnpjDto empresaDto){

        Optional<UserModel> optionalUser = userRepository.findByUsername(cnpjUser);
        EmpresaModel existingCnpj = empresaRepository.findByCnpj(empresaDto.cnpjEmpresa());

        if(existingCnpj != null){
            throw new CnpjRegisteredException();
        }

        if (optionalUser.isPresent()){
            var empresaSaved = saveEmpresa(empresaDto, optionalUser);
            saveQueries(empresaDto, empresaSaved);
        }

        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
    }

    private EmpresaModel saveEmpresa(RegCnpjDto empresaDto, Optional<UserModel> optionalUser){
        EmpresaModel empresa = new EmpresaModel(empresaDto.cnpjEmpresa(), empresaDto.nome(), optionalUser.get());
        empresaRepository.save(empresa);
        return empresa;
    }

    private void saveQueries(RegCnpjDto empresaDto, EmpresaModel empresa){
        if (empresaDto.checkboxSefaz().isPresent()){
            consultaRepository.save(new ConsultasModel(1, empresaDto.frequenciaSefaz(), empresa));
        }
        if (empresaDto.checkboxCndt().isPresent()){
            consultaRepository.save(new ConsultasModel(3, empresaDto.frequenciaCndt(), empresa));
        }
    }
}
