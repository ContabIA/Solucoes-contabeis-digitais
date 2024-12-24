package com.contabia.contabia.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.NotasModel;

@Repository
public interface NotasRepository extends JpaRepository<NotasModel, Long> {

    // Consulta SQL que retorna uma lista de todas as notas de um usario dado o cnpj do mesmo e passando o atributo novo que pode ser true ou false.
    @Query(value = "SELECT n.* FROM notas n JOIN empresa e ON n.id_empresa = e.id JOIN usuarios u ON e.id_usuario = u.id WHERE u.cnpj = :cnpjUser AND n.novo = :novo", nativeQuery = true)
    Optional<List<NotasModel>> findByNovoAndCnpjUser(@Param("novo") boolean novo,@Param("cnpjUser") String cnpjUser);

    @Query(value = "SELECT n.* FROM notas n JOIN empresa e ON n.id_empresa = e.id WHERE e.cnpj = :cnpjEmpresa AND n.data_insercao = :dataInsercao", nativeQuery = true)
    Optional<List<NotasModel>> findNotasEncapsByDataInsercaoAndCnpjEmpresa(@Param("dataInsercao") LocalDate dataInsercao, @Param("cnpjEmpresa") String cnpjEmpresa);
    
}
