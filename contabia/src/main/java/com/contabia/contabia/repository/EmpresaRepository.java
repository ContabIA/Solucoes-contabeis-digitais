package com.contabia.contabia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.EmpresaModel;
import com.contabia.contabia.models.entity.UserModel;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaModel, Long> {

    // Consulta SQL que retorna uma lista de todas as empresas relacionadas de um determinado usuario
    List<EmpresaModel> findByUser(UserModel user);

    // Consulta SQL que retorna uma empresa relacionada a um usuario e que tenha o cnpj passado.
    EmpresaModel findByUserAndCnpj(UserModel user, String cnpj); 

    // Consulta SQL que retorna uma empresa de acordo com o cnpj passado.
    Optional<EmpresaModel> findByCnpj(String cnpj);

    // Processo SQL que deleta uma empresa passado o seu cnpj
    void deleteByCnpj(String cnpjEmpresa);
}
