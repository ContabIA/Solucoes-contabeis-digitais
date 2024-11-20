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
    private EmpresaRepository empresaRepository; //repository das empresas

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    @Autowired
    private ConsultasRepository consultaRepository; //repository das consultas
    
    public ResponseEntity<ExceptionMessage> cadEmpresa(String cnpjUser, RegCnpjDto dadosEmpresa){
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);

        //verifica se o CNPJ informado já está cadastrado no sistema
        Optional<EmpresaModel> empresaByCnpj = empresaRepository.findByCnpj(dadosEmpresa.cnpjEmpresa());
        if(empresaByCnpj.isPresent()){
            throw new CnpjRegisteredException(); //se estiver, retorna mensagem de erro
        }

        if (user.isPresent()){
            //coleta as informações que estão no DTO e adiciona a empresa ao banco de dados
            EmpresaModel empresa = new EmpresaModel(dadosEmpresa.cnpjEmpresa(), dadosEmpresa.nome(), user.get());
            empresaRepository.save(empresa);

            //caso tenha sido marcado alguma consulta na página, esta também será adicionada ao banco de dados
            if (dadosEmpresa.checkboxSefaz().isPresent()){
                consultaRepository.save(new ConsultasModel(1, dadosEmpresa.frequenciaSefaz(), empresa));
            }

            if (dadosEmpresa.checkboxCndt().isPresent()){
                consultaRepository.save(new ConsultasModel(3, dadosEmpresa.frequenciaCndt(), empresa));
            }
        }

        return ResponseEntity.ok().body(new ExceptionMessage(HttpStatus.OK, "ok"));
    }
}
