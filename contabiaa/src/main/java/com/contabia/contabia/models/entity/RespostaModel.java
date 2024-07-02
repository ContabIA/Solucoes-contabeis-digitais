package com.contabia.contabia.models.entity;

import java.util.Date;

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

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Resposta")
public class RespostaModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private byte status;

    @Column(unique = false, nullable = false)
    private Date data;

    @Column(unique = false, nullable = false)
    private boolean novo;

    @ManyToOne
    @JoinColumn(name = "idConsulta", nullable = false)
    private ConsultasModel consulta;
}