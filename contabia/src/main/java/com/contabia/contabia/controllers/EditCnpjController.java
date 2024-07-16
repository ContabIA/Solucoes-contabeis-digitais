package com.contabia.contabia.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.contabia.contabia.models.dto.RegCnpjDto;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

    /*
     * Classe controller responsável por gerenciar a página de edição de uma empresa cadastrada e alteração das informações
     * no banco de dados.
     * 
     * rotas:
     *  /editCnpj/ (GET) -> exibe a página de edição e envia para o thymeleaf as informações atuais que devem ser exibidas.
     *  
     *  /editCnpj/ (PUT) -> recebe os dados da página de edição e atualiza os dados já existentes e cria novas consultas caso 
     *  necessário. Retorna o cnpj do usuário.
    */

@Controller
@RequestMapping("/editCnpj")
public class EditCnpjController {

    @Autowired
    private ConsultasRepository consultasRepository; //repository das consultas

    @Autowired
    private EmpresaRepository empresaRepository; //repository das empresas

    @Autowired
    private UserRepository userRepository; //repository dos usuários

    @GetMapping
    public String editCnpj(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, Model model) {
        Optional<UserModel> user = userRepository.findByCnpj(cnpjUser);

        if(user.isPresent()){
            var userEncontrado = user.get();

            EmpresaModel empresa = empresaRepository.findByUserAndCnpj(userEncontrado, cnpjEmpresa);
            
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
        }
        model.addAttribute("cnpjUser", cnpjUser); //envia para o thymeleaf o cnpj do usuário
        model.addAttribute("cnpjEmpresa", cnpjEmpresa); //envia para o thymeleaf o cnpj da empresa editada
        return "editCnpj";
    }
    

    @PutMapping
    @Transactional
    public ResponseEntity<String> criarEdicao(@RequestParam("cnpjUser") String cnpjUser, @RequestParam("cnpjEmpresa") String cnpjEmpresa, @Valid @RequestBody RegCnpjDto dadosEmpresa) {

        //obtem o objeto da empresa que está sendo editada
        Optional<EmpresaModel> empresa =  empresaRepository.findByCnpj(cnpjEmpresa);
        var empresaEncontrada = empresa.get();

        //caso exista, coleta a consulta sefaz agendada pela empresa que está sendo editada
        Optional<ConsultasModel> optionalConsultaSefaz = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 1);

        //caso exista, coleta a consulta CNDT agendada pela empresa que está sendo editada
        Optional<ConsultasModel> optionalConsultaCndt = consultasRepository.findConsultaByCnpjAndTipoConsulta(cnpjEmpresa, 3);

        if(dadosEmpresa.checkboxSefaz().get() == true){
            //caso a checkbox da pesquisa sefaz esteja marcada na página, e também esteja agendada no banco de dados, é feita a atualização de sua frequência.
            if(optionalConsultaSefaz.isPresent()){
                var consultaSefaz = optionalConsultaSefaz.get();
                consultaSefaz.editConsulta(dadosEmpresa.frequenciaSefaz());
                consultasRepository.save(consultaSefaz);
            //No entanto, caso não a consulta sefaz não exista no banco de dados, ela é criada neste momento.
            }else{
                consultasRepository.save(new ConsultasModel(1, dadosEmpresa.frequenciaSefaz(), empresaEncontrada));
            }
        //Já se a consulta existe no banco de dados e foi desmarcada na página de edição, esta consulta é deletada do banco de dados.
        }else{
            if(optionalConsultaSefaz.isPresent()){  
                consultasRepository.deleteById(optionalConsultaSefaz.get().getId());
            }
        }

        if(dadosEmpresa.checkboxCndt().get() == true){
            //caso a checkbox da pesquisa CNDT esteja marcada na página, e também esteja agendada no banco de dados, é feita a atualização de sua frequência.
            if(optionalConsultaCndt.isPresent()){
                var consultaCndt = optionalConsultaCndt.get();
                consultaCndt.editConsulta(dadosEmpresa.frequenciaCndt());
                consultasRepository.save(consultaCndt);
            //No entanto, caso não a consulta CNDT não exista no banco de dados, ela é criada neste momento.
            }else{
                consultasRepository.save(new ConsultasModel(3, dadosEmpresa.frequenciaCndt(), empresaEncontrada));
            }
        //Já se a consulta existe no banco de dados e foi desmarcada na página de edição, esta consulta é deletada do banco de dados.
        }else{
            if(optionalConsultaCndt.isPresent()){
                consultasRepository.deleteById(optionalConsultaCndt.get().getId());
            }
        }

        //atuailza os dados da tabela empresa com os dados recebidos da página
        empresaEncontrada.editEmpresa(dadosEmpresa.cnpjEmpresa(), dadosEmpresa.nome()); 
        empresaRepository.save(empresaEncontrada); //salva as alterações no banco de dados

        return ResponseEntity.ok().body(cnpjUser); //retorna para o cnpj do usuário para o JS
    }
}
