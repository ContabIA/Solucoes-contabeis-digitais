package com.contabia.contabia.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Consulta")
public class ConsultasModel{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = false, nullable = false)
    private int tipoConsulta;

    @Column(unique = false, nullable = false)
    private int frequencia;

    @ManyToOne
    @JoinColumn(name="idEmpresa", nullable = false)
    private EmpresaModel empresaConsulta;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostaModel> respostas;

    public ConsultasModel(int tipoConsulta, int frequencia, EmpresaModel empresa) {
        this.tipoConsulta = tipoConsulta;
        this.frequencia = frequencia;
        this.empresaConsulta = empresa;
    }
}