package com.contabia.contabia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    // Consulta SQL que retorna um usuário dado o cnpj dele.
    Optional<UserModel> findByCnpj(String cnpj);

    Optional<UserModel> findByEmail(String cnpj);

    Optional<UserModel> findByUserSefaz(String cnpj);

}
