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

/*
 * Classe model responsável por fazer a representação da tabela empresa do banco de dados.
 * 
 * Atributos:
 * id: identificador da instância
 * cnpj: cnpj da empresa
 * nome: nome da empresa
 * user: usuario cuja a empresa está relacionada
 * 
*/

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

    // Declaração de relação n:1 da entidade empresa com a entidade usuario no banco de dados.
    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = false)
    private UserModel user;

    // Declaração de relação 1:n da entidade empresa com a entidade consultas no banco de dados.
    @OneToMany(mappedBy = "empresaConsulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultasModel> consultas;

    // Declaração de relação 1:n da entidade empresa com a entidade notas no banco de dados.
    @OneToMany(mappedBy = "empresaNotas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotasModel> notas;

    // Construtor
    public EmpresaModel(String cnpj, String nome, UserModel user){
        this.cnpj = cnpj;
        this.nome = nome;
        this.user = user;
    }
    
    // Método que edita informações da empresa.
    public void editEmpresa(String cnpj, String nome){
        this.cnpj = cnpj;
        this.nome = nome;
    }
}
