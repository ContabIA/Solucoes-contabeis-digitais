package com.contabia.contabia.models.entity;

import java.time.LocalDate;

import com.contabia.contabia.models.dto.RespostaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Classe model responsável por fazer a representação da tabela resposta do banco de dados.
 * 
 * Atributos:
 * id: identificador da instância
 * status: status da resposta(0 = negativa, 1=positiva)
 * data: data em que foi feita a consulta
 * novo: boolean que identifica se a resposta já foi vizualizada(0) ou não(0) pelo usuário 
 * consulta: consulta cuja a resposta está relacionada
 * 
*/

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Resposta")
public class RespostaModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = false, nullable = false)
    private byte status;

    @Column(unique = false, nullable = false)
    private LocalDate data;

    @Setter
    @Column(unique = false, nullable = false)
    private boolean novo;

    // Método que retorna o atributo novo da resposta.
    public boolean getNovo(){
        return this.novo;
    }

   // Declaração de relação n:1 da entidade resposta com a entidade consulta no banco de dados.
    @ManyToOne
    @JoinColumn(name = "idConsulta", nullable = false)
    private ConsultasModel consulta;

    // Construtor com base no RespostaDto e na consulta que ela esta relacionada.
    public RespostaModel(RespostaDto dados, ConsultasModel consulta){
        this.status = dados.status();
        this.novo = dados.novo();
        this.data = dados.data();
        this.consulta = consulta;
    }
}