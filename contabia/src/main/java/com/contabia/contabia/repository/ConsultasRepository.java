package com.contabia.contabia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.ConsultasModel;

@Repository
public interface ConsultasRepository extends JpaRepository<ConsultasModel, Long>{
    
}
