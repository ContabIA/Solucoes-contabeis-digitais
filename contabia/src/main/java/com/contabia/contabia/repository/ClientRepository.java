package com.contabia.contabia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long>{
    Optional<ClientModel> findByUsername(String username);
}
