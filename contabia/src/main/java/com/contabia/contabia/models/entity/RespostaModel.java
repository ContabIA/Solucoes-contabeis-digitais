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

    public boolean getNovo(){
        return this.novo;
    }
    @ManyToOne
    @JoinColumn(name = "idConsulta", nullable = false)
    private ConsultasModel consulta;

    public RespostaModel(RespostaDto dados, ConsultasModel consulta){
        this.status = dados.status();
        this.novo = dados.novo();
        this.data = dados.data();
        this.consulta = consulta;
    }
}