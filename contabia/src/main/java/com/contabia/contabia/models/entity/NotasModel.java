package com.contabia.contabia.models.entity;


import java.time.LocalDate;

import com.contabia.contabia.models.dto.NotasDto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Classe model responsável por fazer a representação da tabela notas do banco de dados.
 * 
 * Atributos:
 * id: número único da nota fiscal
 * data: data em que a nota fiscal foi publicada no Sefaz
 * novo: boolean que identifica se a nota fiscal já foi vizualizada(0) ou não(0) pelo usuário 
 * serie: série da nota fiscal
 * valor: valor total da nota fiscal
 * nomeEmitente: nome da empresa que emitiu a nota fiscal
 * situacao: situação da nota fiscal (Autorizada ou Cancelada)
 * empresaNotas: empresa cuja a nota fiscal está relacionada
 * 
*/

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Notas")
public class NotasModel {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = false, nullable = false)
    private LocalDate data;

    @Column(unique = false, nullable = false)
    private int serie;

    @Column(unique = false, nullable = false)
    private String nomeEmitente;

    @Column(unique = false, nullable = false)
    private String situacao;

    @Column(unique = false, nullable = false)
    private double valor;

    @Setter
    @Column(unique = false, nullable = false)
    private boolean novo;

    // Declaração de relação n:1 da entidade nota com a entidade empresa no banco de dados.
    @ManyToOne
    @JoinColumn(name="idEmpresa", nullable = false)
    private EmpresaModel empresaNotas;

    // Construtor com base no NotasDto e nas variaveis novo e empresaNotas passadas
    public NotasModel(NotasDto dados, boolean novo, EmpresaModel empresaNotas){
        this.id = dados.id();
        this.data = dados.data();
        this.nomeEmitente = dados.nomeEmitente();
        this.serie = dados.serie();
        this.situacao = dados.situacao();
        this.valor = dados.valor();
        this.novo = novo;
        this.empresaNotas = empresaNotas;
    }

}