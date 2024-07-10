package com.contabia.contabia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contabia.contabia.models.entity.NotasModel;


public interface NotasRepository extends JpaRepository<NotasModel, Long> {


    @Query(value = "SELECT n.* FROM notas n JOIN empresa e ON n.id_empresa = e.id JOIN usuarios u ON e.id_usuario = u.id WHERE u.cnpj = :cnpjUser AND n.novo = :novo", nativeQuery = true)
    Optional<List<NotasModel>> findByNovoAndCnpjUser(@Param("novo") boolean novo,@Param("cnpjUser") String cnpjUser);

    
    
}
