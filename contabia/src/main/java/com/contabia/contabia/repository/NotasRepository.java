package com.contabia.contabia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contabia.contabia.models.entity.NotasModel;

public interface NotasRepository extends JpaRepository<NotasModel, Long> {

}
