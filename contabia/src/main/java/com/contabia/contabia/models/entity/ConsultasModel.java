package com.contabia.contabia.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Consulta")
public class ConsultasModel{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private int tipoConsulta;

    @Column(unique = false, nullable = false)
    private int frequencia;

    @ManyToOne
    @JoinColumn(name="idEmpresa", nullable = false)
    private EmpresaModel empresa;

    public ConsultasModel(int tipoConsulta, int frequencia, EmpresaModel empresa) {
        this.tipoConsulta = tipoConsulta;
        this.frequencia = frequencia;
        this.empresa = empresa;
    }

    
    
}