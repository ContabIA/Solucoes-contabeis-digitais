package com.contabia.contabia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

    /*
     * Classe controller responsável por gereciar a página de cadastro de empresas e receber as informações para salvar as empresas e suas respectivas consultas no banco de dados.
     * 
     * rotas:
     *  /cadastroCnpj/ (GET) -> exibe a página de cadastro de empresa
     * 
     *  /cadastroCnpj/ (POST) -> recebe os dados que devem ser salvos na tabela "empresa" e "consulta" e realiza a persistência dos dados
    */

@Controller
@RequestMapping("/cadastroCnpj")
public class RegCnpjController {

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    @Autowired
    private ConsultasRepository consultaRepository; //repository das consultas

    @GetMapping
    public String cadastroCnpj(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        model.addAttribute("cnpjUser", cnpjUser);//envio padrão do cnpj do usuário
        return "cadastroCnpj"; //exibe a página de cadastro de empresa
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity<ExceptionMessage> addEmpresa(@RequestParam("cnpjUser") String cnpjUser, @Valid @RequestBody RegCnpjDto dadosEmpresa) {

        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);

        //verifica se o CNPJ informado já está cadastrado no sistema
        Optional<EmpresaModel> empresaByCnpj = empresaRepository.findByCnpj(dadosEmpresa.cnpjEmpresa());
        if(empresaByCnpj.isPresent()){
            throw new CnpjRegisteredException(); //se estiver, retorna mensagem de erro
        }

        if (user.isPresent()){
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
