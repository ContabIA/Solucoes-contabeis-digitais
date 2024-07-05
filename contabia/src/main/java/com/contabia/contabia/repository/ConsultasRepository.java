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
    
    List<ConsultasModel> findByEmpresaConsulta(EmpresaModel empresa);

    @Query(value = "SELECT id_empresa FROM consulta c WHERE RIGHT(c.id, 1) = :ultimoDigito AND c.frequencia = :frequencia AND c.tipo_consulta = :tipoConsulta;", nativeQuery = true)
    List<Long> empresasOfDay(@Param("ultimoDigito") int ultimoDigito, @Param("frequencia") int frequencia, @Param("tipoConsulta") int tipoConsulta);

    @Query(value = "SELECT c.* FROM empresa e JOIN consulta c ON e.id = c.id_empresa WHERE e.cnpj = :cnpjEmpresa AND c.tipo_consulta = 3", nativeQuery = true)
    Optional<ConsultasModel> findConsultaByCnpj(@Param("cnpjEmpresa") String cnpjEmpresa);
    
}
