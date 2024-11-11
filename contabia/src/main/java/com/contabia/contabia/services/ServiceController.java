package com.contabia.contabia.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contabia.contabia.models.dto.DadosLoginDto;
import com.contabia.contabia.models.dto.ListaNotasDto;
import com.contabia.contabia.models.dto.ListaRespostaDto;
import com.contabia.contabia.models.dto.RespostaDto;
import com.contabia.contabia.models.dto.NotasDto;
import com.contabia.contabia.models.dto.ServiceDto;
import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.NotasModel;
import com.contabia.contabia.models.entity.RespostaModel;
import com.contabia.contabia.repository.ConsultasRepository;
import com.contabia.contabia.repository.RespostaRepository;
import com.contabia.contabia.repository.EmpresaRepository;
import com.contabia.contabia.repository.NotasRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Classe rest controller responsável por receber e gerenciar requisições de aplicações externas e criar métodos auxiliares. 
 * 
 * Rotas:
 *  /service/ (GET) -> Rotas para aplicações externas receberem informações para executar as automações.
 * 
 *          /getCnpj/ -> Envia para a aplicação que requisitar todos os cnpj's das consultas que devem ser feitas naquele dia, de acordo com os paramêtros que vão ser mandandos na requisição.
 *          /getDadosLogin/ -> Envia para a aplicação que requisitar os dados do login no site do Sefaz para o cnpj que for enviado como parâmetro.
 *  
 *  /service/ (PUT) -> Recebe a resposta de automações executadas na aplicação externa com dados que devem ser incluidos no banco de dados.
 * 
 *          /respSefaz/ -> Recebe uma lista de notas que devem ser incluidas no banco.
 *          /respCndt/ -> Recebe uma lista de respostas que devem ser incluidas no banco.
*/

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @GetMapping("/getCnpj")
    public ResponseEntity<ServiceDto> getCnpj(@RequestParam("ultimoDigito") int ultimoDigito, @RequestParam("tamanhoFinal") int tamanhoFinal, @RequestParam("frequencia") String frequencia, @RequestParam("tipoConsulta") int tipoConsulta) {

        int idConsulta = ultimoDigito; // id da consulta que obrigatoriamente será coletado já que é igual ao final de id pedido.
        String strUltimoDigito = ultimoDigito + ""; // Transformando o ultimo digito em String.
        List<Long> listIdEmpresa = new ArrayList<>(); // Lista que receberá os id empresas do dia.

        // Verifica se a frequencia é mensal e se o ultimo digito é menor do que dez. Se for deve-se pesquisar por todos os id terminados em 0+ultimoDigito. (Ex.: se o id for 1 deve-se procurar por todos os id terminados em 01).
        if((frequencia.equals("2")) && ultimoDigito < 10){
            strUltimoDigito = "0" + strUltimoDigito; 
        }

        // Verifica se a requisição quer todos os cnpj's (dia 1) ou quer de acordo com os parâmetros.
        if (frequencia.equals("")) {
            // Busca todos os id cujo o tipo de consulta seja 1.
            listIdEmpresa = consultasRepository.getIdEmpresaByTipoConsulta(tipoConsulta);
        } else {
            // Busca no banco todos os Id's das empresa que devem ter consultas executadas hoje de acordo com os paramêtros de requisição enviado pela aplicação externa.
            listIdEmpresa = consultasRepository.empresasOfDay(idConsulta, tamanhoFinal, strUltimoDigito, frequencia, tipoConsulta);
            System.out.println("\n" + strUltimoDigito + "\n");
        }

        
        List<String> cnpjs = new ArrayList<>(); // Lista para armazenar os cnpj's das empresas
        // Coleta e insere na lista o cnpj para cada empresa encontrada.
        for (Long id : listIdEmpresa) {
            Optional<EmpresaModel> empresa = empresaRepository.findById(id);
            if(empresa.isPresent()){
                cnpjs.add(empresa.get().getCnpj());
                //System.out.println("\n" + empresa.get().getCnpj() + "\n");
            }
            System.out.println("\n" + cnpjs + "\n");
        }
        return ResponseEntity.ok().body(new ServiceDto(cnpjs)); // Retorno da requisição com lista de cnps's como body.
    } 

    @GetMapping("/getDadosLogin")
    public ResponseEntity<DadosLoginDto> getDadosLogin(@RequestParam("cnpjEmpresa") String cnpjEmpresa){
        
        Optional<List<String>> optionalDadosLogin = empresaRepository.findDadosLoginByCnpjEmpresa(cnpjEmpresa); // Verifica qual usuário está relacionado ao cnpj enviado e retorna uma lista com usuário e senha sefaz.
        if (optionalDadosLogin.isPresent()){
            return ResponseEntity.ok().body(new DadosLoginDto(optionalDadosLogin.get())); // Retorna os dadosLogin
        }
        return ResponseEntity.badRequest().body(null); // Retorna nulo caso não exista os DadosLogin.
    }

    @PostMapping("/respSefaz")
    public ResponseEntity<String> respSefaz(@RequestBody ListaNotasDto listaNotas) {
        
        // Tenta inserir no banco cada uma das notas enviadas pela aplicação externa.
        for (NotasDto nota : listaNotas.listaNotas()) {

            Optional<EmpresaModel> empresa = empresaRepository.findByCnpj(nota.cnpjEmpresa()); // Coleta empresa que esta associada à nota.

            LocalDate diaAtual = LocalDate.now();
            
            NotasModel novaNota = new NotasModel();
            // Confere se nota é do mês imediatamente passado, logo essa não deve ser tratada como alteração e inserida com o argumento novo = false.
            if (diaAtual.getDayOfMonth() == 1){
                
                if (nota.data().getMonthValue() == diaAtual.getMonthValue() - 1){
                    novaNota = new NotasModel(nota, false, empresa.get()); // Cria modelo de nota para inserir no banco.
                } else {
                    novaNota = new NotasModel(nota, true, empresa.get()); // Cria modelo de nota para inserir no banco.
                } 
                
            } else {
                novaNota = new NotasModel(nota, true, empresa.get());
            }

            // Verifica se nota já existe no banco de dados e se não existir à insere.
            if (notasRepository.findById(novaNota.getId()).isPresent()){
                continue;
            } else {
                notasRepository.save(novaNota);
            }
            
        }
        return ResponseEntity.ok().body("deu certo!");
    }

    @PostMapping("/respCndt")
    public ResponseEntity<String> respCndt(@RequestBody ListaRespostaDto listaResp) {
        
        // Insere no banco cada uma das repostas enviadas pela aplicação externa
        for (RespostaDto resposta : listaResp.listaRespostas()) {

            Optional<ConsultasModel> consulta = consultasRepository.findConsultaByCnpjAndTipoConsulta(resposta.cnpjEmpresa(), 3); // Coleta consulta que está relacionada à resposta.
            RespostaModel novaResposta = new RespostaModel(resposta, consulta.get()); // Cria modelo de resposta para inserir no banco.
            respostaRepository.save(novaResposta); // Insere resposta no banco.
            
        }
        return ResponseEntity.ok().body("ok");
    }

}
