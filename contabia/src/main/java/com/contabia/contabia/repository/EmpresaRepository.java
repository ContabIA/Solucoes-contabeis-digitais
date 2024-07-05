package com.contabia.contabia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {

    List<EmpresaModel> findByUser(UserModel user);

    EmpresaModel findByUserAndCnpj(UserModel user, String cnpj); 

    Optional<EmpresaModel> findByCnpj(String cnpj);

    void deleteByCnpj(String cnpjEmpresa);
}
