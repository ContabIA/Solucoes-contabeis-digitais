package com.contabia.contabia.models.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Notas")
public class NotasModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private Date data;

    @Column(unique = false, nullable = false)
    private int serie;

    @Column(unique = false, nullable = false)
    private String cnpjEmitente;

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
    private EmpresaModel empresa;
    
}