package com.contabia.contabia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;

@Repository
public interface ConsultasRepository extends JpaRepository<ConsultasModel, Long>{
    
    // Consulta SQL que retorna com todas as consultas relacionadas a uma determinada empresa.
    List<ConsultasModel> findByEmpresaConsulta(EmpresaModel empresa);

    // Consulta SQL que retorna uma lista de todos os cnpj's de empresas que tem consultas no dia, de acordo com os paramêtros passados (final do id da consulta, tipo consulta e frequencia).
    @Query(value = "SELECT id_empresa FROM consulta c WHERE c.id like %:ultimoDigito AND c.frequencia like %:frequencia AND c.tipo_consulta = :tipoConsulta;", nativeQuery = true)
    List<Long> empresasOfDay(@Param("ultimoDigito") String ultimoDigito, @Param("frequencia") String frequencia, @Param("tipoConsulta") int tipoConsulta);

    // Consulta SQL que retorna uma consulta relacionada a uma empresa passando o seu tipo.
    @Query(value = "SELECT c.* FROM empresa e JOIN consulta c ON e.id = c.id_empresa WHERE e.cnpj = :cnpjEmpresa AND c.tipo_consulta = :tipoConsulta", nativeQuery = true)
    Optional<ConsultasModel> findConsultaByCnpjAndTipoConsulta(@Param("cnpjEmpresa") String cnpjEmpresa, @Param("tipoConsulta") int tipoConsulta); 
}
