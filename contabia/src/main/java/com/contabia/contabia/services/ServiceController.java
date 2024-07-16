package com.contabia.contabia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * Classe rest controller responsável por receber e gerenciar requisições de aplicações externas. 
 * 
 * Rotas:
 *  /service/ (GET) -> Envia para a aplicação que requisitar todos os cnpj's das consultas que devem ser feitas naquele dia, de acordo com os paramêtros que vão ser mandandos na requisição.
 *  
 *  /service/ (PUT) -> Recebe a resposta de automações executadas na aplicação externa com dados que devem ser incluidos no banco de dados.
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
    public ResponseEntity<ServiceDto> getCnpj(@RequestParam("tamanhoFinal") int tamanhoFinal, @RequestParam("ultimoDigito") int ultimoDigito, @RequestParam("frequencia") int frequencia, @RequestParam("tipoConsulta") int tipoConsulta) {

        // Busca no banco todos os Id's das empresa que devem ter consultas executadas hoje de acordo com os paramêtros de requisição enviado pela aplicação externa.
        List<Long> listIdEmpresa = consultasRepository.empresasOfDay(tamanhoFinal, ultimoDigito, frequencia, tipoConsulta);

        List<String> cnpjs = new ArrayList<>(); // Lista para armazenar os cnpj's das empresas
        // Coleta e insere na lista o cnpj para cada empresa encontrada.
        for (Long id : listIdEmpresa) {
            Optional<EmpresaModel> empresa = empresaRepository.findById(id);
            if(empresa.isPresent()){
                cnpjs.add(empresa.get().getCnpj());
            }
        }

        return ResponseEntity.ok().body(new ServiceDto(cnpjs)); // Retorno da requisição com lista de cnps's como body.
    } 

    @PostMapping("/respSefaz")
    public ResponseEntity<String> respSefaz(@RequestBody ListaNotasDto listaNotas) {
        
        // Tenta inserir no banco cada uma das notas enviadas pela aplicação externa.
        for (NotasDto nota : listaNotas.listaNotas()) {

            Optional<EmpresaModel> empresa = empresaRepository.findByCnpj(nota.cnpjEmpresa()); // Coleta empresa que esta associada à nota.
            NotasModel novaNota = new NotasModel(nota, true, empresa.get()); // Cria modelo de nota para inserir no banco.
            
            // Tenta inserir no banco a nota, caso não seja inserida nada acontece pois se ela já está no banco significa que ela não é uma alteração.
            try {
                notasRepository.save(novaNota); // Insere resposta no banco.
            } catch (Exception e) {
                continue;
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
        return ResponseEntity.ok().body("deu certo!");
    }

}