package com.contabia.contabia.models.entity;

import java.util.Date;

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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Notas")
public class NotasModel {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = false, nullable = false)
    private String data;

    @Column(unique = false, nullable = false)
    private int serie;

    // @Column(unique = false, nullable = false)
    // private String cnpjEmitente;

    @Column(unique = false, nullable = false)
    private String nomeEmitente;

    @Column(unique = false, nullable = false)
    private int situacao;

    @Column(unique = false, nullable = false)
    private double valor;

    @Column(unique = false, nullable = false)
    private boolean novo;

    @ManyToOne
    @JoinColumn(name="idEmpresa", nullable = false)
    private EmpresaModel empresaNotas;

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