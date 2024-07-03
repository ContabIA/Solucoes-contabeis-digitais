package com.contabia.contabia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.EmpresaModel;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {

    
}
