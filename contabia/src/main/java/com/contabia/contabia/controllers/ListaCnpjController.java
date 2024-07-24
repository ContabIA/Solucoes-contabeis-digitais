package com.contabia.contabia.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.EmpresaDto;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;

    /*
     * Classe controller responsável por gerenciar a página que exibe a lista de empresas cadastradas pelo usuário.
     * 
     * rotas:
     *  /listaCnpj/ (GET) -> exibe a página de listagem de empresas cadastradas e envia para o thymeleaf uma lista das empresas cadastradas pelo usuário atual.
     * 
     *  /listaCnpj/ (DELETE) -> recebe o CNPJ da empresa que será deletada e realiza a exclusão no banco de dados.
    */

@Controller
@RequestMapping("/listaCnpj")
public class ListaCnpjController {

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    @GetMapping
    public String listaCnpj(@RequestParam("cnpjUser") String cnpjUser, Model model) {
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);
        List<EmpresaDto> infos = new ArrayList<>(); //lista de informações que será enviado para o thymeleaf

        if(user.isPresent()){
            List<EmpresaModel> empresas = empresaRepository.findByUser(user.get()); //pesquisa todas as empresas do usuário atual

            //formatação do nome que será exibido na página e adição à lista
            for (EmpresaModel empresaModel : empresas) {
                var nome = (empresaModel.getNome() + " - " + empresaModel.getCnpj());
                
                infos.add(0, new EmpresaDto(nome, empresaModel.getCnpj()));
            }
            model.addAttribute("empresasInfos", infos); //envia para o thymeleaf a lista de informações
        }
        model.addAttribute("cnpjUser", cnpjUser); //envio padrão do cnpj do usuário
        return "listaCnpj";
    }

    @DeleteMapping
    @Transactional
    public String delCnpj(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa){
        //recebe o cnpj do usuário e da empresa que será deletada como parâmetro de requisição e realiza a exclusão
        empresaRepository.deleteByCnpj(cnpjEmpresa);
        return "listaCnpj"; //exibe novamente a mesma página atualizada
    }
}
