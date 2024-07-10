package com.contabia.contabia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contabia.contabia.models.entity.RespostaModel;

public interface RespostaRepository extends JpaRepository<RespostaModel, Long>{

    @Query(value = "SELECT r.* FROM resposta r JOIN consulta c ON r.id_consulta = c.id JOIN empresa e ON c.id_empresa = e.id JOIN usuarios u ON e.id_usuario = u.id WHERE u.cnpj = :cnpjUser AND r.novo = :novo AND r.status = :status", nativeQuery = true)
    Optional<List<RespostaModel>> findByNovoAndCnpjUserAndStatus(@Param("novo") boolean novo,@Param("cnpjUser") String cnpjUser, @Param("status") int status );
}
