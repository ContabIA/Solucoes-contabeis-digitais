package com.contabia.contabia.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.contabia.contabia.exceptions.CnpjRegisteredException;
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
public class EditCnpjService {

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    @Autowired
    private ConsultasRepository consultasRepository; //repository das consultas

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    
    public String enviarDadosAtuais(String cnpjUser, String cnpjEmpresa, Model model){
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);

        if(user.isPresent()){
            var userEncontrado = user.get();

            //erro acontece aq
            EmpresaModel empresa = empresaRepository.findByUserAndCnpj(userEncontrado, cnpjEmpresa);

            if(empresa != null){
                model.addAttribute("nomeEmpresa", empresa.getNome()); //envia para o thymeleaf o nome atual da empresa
                model.addAttribute("cnpjEmpresa", cnpjEmpresa); //envia para o thymeleaf o cnpj atual da empresa

                //obtem as consultas referentes à empresa em questão
                List<ConsultasModel> consultas = consultasRepository.findByEmpresaConsulta(empresa);

                //verifica quais consultas já estão programadas para a empresa
                for (ConsultasModel consultasModel : consultas) {                
                    if(consultasModel.getTipoConsulta() == 1) model.addAttribute("sefazStatus", true);
                    if(consultasModel.getTipoConsulta() == 3) model.addAttribute("cndtStatus", true);
                }

                Map<Integer, String> nomeAtributo = new HashMap<>();
                nomeAtributo.put(1, "freqSefaz");
                nomeAtributo.put(3, "freqCndt");

                //envia para o thymeleaf a frequência atual que as consultas possuem
                //Isso é feito com base no tipo das consultas da lista "consultas" e no nome salvo no HashMap "nomeAtributo"
                for(int i = 0; i < consultas.size(); i++){
                    model.addAttribute(nomeAtributo.get(consultas.get(i).getTipoConsulta()) + Integer.toString(consultas.get(i).getFrequencia()), true);
                }

                return "editCnpj";
            }
        }
        return "redirect:/logout";
    }

    private void atualizaConsultaCndt(String cnpjEmpresa, RegCnpjDto dadosEmpresa, EmpresaModel empresa){

        //caso exista, coleta a consulta CNDT agendada pela empresa que está sendo editada
        Optional<ConsultasModel> optionalConsultaCndt = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 3);

        if(dadosEmpresa.checkboxCndt().get() == true){
            //caso a checkbox da pesquisa CNDT esteja marcada na página, e também esteja agendada no banco de dados, é feita a atualização de sua frequência.
            if(optionalConsultaCndt.isPresent()){
                var consultaCndt = optionalConsultaCndt.get();
                consultaCndt.editConsulta(dadosEmpresa.frequenciaCndt());
                consultasRepository.save(consultaCndt);
            //No entanto, caso não a consulta CNDT não exista no banco de dados, ela é criada neste momento.
            }else{
                consultasRepository.save(new ConsultasModel(3, dadosEmpresa.frequenciaCndt(), empresa));
            }
        //Já se a consulta existe no banco de dados e foi desmarcada na página de edição, esta consulta é deletada do banco de dados.
        }else{
            if(optionalConsultaCndt.isPresent()){
                consultasRepository.deleteById(optionalConsultaCndt.get().getId());
            }
        }
    }

    private void atualizaConsultaSefaz(String cnpjEmpresa, RegCnpjDto dadosEmpresa, EmpresaModel empresa){

        //caso exista, coleta a consulta sefaz agendada pela empresa que está sendo editada
        Optional<ConsultasModel> optionalConsultaSefaz = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 1);

        if(dadosEmpresa.checkboxSefaz().get() == true){
            //caso a checkbox da pesquisa sefaz esteja marcada na página, e também esteja agendada no banco de dados, é feita a atualização de sua frequência.
            if(optionalConsultaSefaz.isPresent()){
                var consultaSefaz = optionalConsultaSefaz.get();
                consultaSefaz.editConsulta(dadosEmpresa.frequenciaSefaz());
                consultasRepository.save(consultaSefaz);
            //No entanto, caso não a consulta sefaz não exista no banco de dados, ela é criada neste momento.
            }else{
                consultasRepository.save(new ConsultasModel(1, dadosEmpresa.frequenciaSefaz(), empresa));
            }
        //Já se a consulta existe no banco de dados e foi desmarcada na página de edição, esta consulta é deletada do banco de dados.
        }else{
            if(optionalConsultaSefaz.isPresent()){  
                consultasRepository.deleteById(optionalConsultaSefaz.get().getId());
            }
        }
    }

    private void atualizaEmpresa(RegCnpjDto dadosEmpresa, EmpresaModel empresa){
        //atuailza os dados da tabela empresa com os dados recebidos da página
        empresa.editEmpresa(dadosEmpresa.cnpjEmpresa(), dadosEmpresa.nome()); 
        empresaRepository.save(empresa); //salva as alterações no banco de dados
    }

    public void atualizar(RegCnpjDto dadosEmpresa, String cnpjEmpresa){
        //obtem o objeto da empresa que está sendo editada
        Optional<EmpresaModel> empresaOpt =  empresaRepository.findByCnpj(cnpjEmpresa);
        var empresa = empresaOpt.get();

        //verificação se o cnpj que está sendo atualizado já está cadastrado no sistema
        Optional<EmpresaModel> empresaByCnpj = empresaRepository.findByCnpj(dadosEmpresa.cnpjEmpresa());
        if(empresaByCnpj.isPresent() && !(dadosEmpresa.cnpjEmpresa().equals(cnpjEmpresa))){
            throw new CnpjRegisteredException();
        }

        atualizaConsultaCndt(cnpjEmpresa, dadosEmpresa, empresa);
        atualizaConsultaSefaz(cnpjEmpresa, dadosEmpresa, empresa);
        atualizaEmpresa(dadosEmpresa, empresa);
    }
}

//está acontecendo um erro quando o usuário muda o cnpj da empresa na url de editar empresa