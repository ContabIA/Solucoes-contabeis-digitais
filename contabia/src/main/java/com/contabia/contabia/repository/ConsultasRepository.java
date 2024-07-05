package com.contabia.contabia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.ConsultasModel;
import com.contabia.contabia.models.entity.EmpresaModel;

@Repository
public interface ConsultasRepository extends JpaRepository<ConsultasModel, Long>{
    
    List<ConsultasModel> findByEmpresaConsulta(EmpresaModel empresa);
}
