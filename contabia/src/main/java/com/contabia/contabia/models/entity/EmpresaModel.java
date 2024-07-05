package com.contabia.contabia.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Empresa")
public class EmpresaModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(unique = false, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "empresaConsulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultasModel> consultas;

    @OneToMany(mappedBy = "empresaNotas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotasModel> notas;

    public EmpresaModel(String cnpj, String nome, UserModel user){
        this.cnpj = cnpj;
        this.nome = nome;
        this.user = user;
    }
}
