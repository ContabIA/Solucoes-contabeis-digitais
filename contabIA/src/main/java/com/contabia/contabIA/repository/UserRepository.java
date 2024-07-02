package com.contabia.contabia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

}
