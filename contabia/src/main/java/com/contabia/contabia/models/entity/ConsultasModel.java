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
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
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

    // Declaração de relação n:1 da entidade consulta com a entidade empresa no banco de dados.
    @ManyToOne
    @JoinColumn(name="idEmpresa", nullable = false)
    private EmpresaModel empresaConsulta;

    // Declaração de relação 1:n da entidade consulta com a entidade respostas no banco de dados.
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostaModel> respostas;

    // Construtor
    public ConsultasModel(int tipoConsulta, int frequencia, EmpresaModel empresa) {
        this.tipoConsulta = tipoConsulta;
        this.frequencia = frequencia;
        this.empresaConsulta = empresa;
    }
    
    public void editConsulta(int frequencia){
        this.frequencia = frequencia;
    }
}