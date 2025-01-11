package com.contabia.contabia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contabia.contabia.models.entity.ApplicationModel;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long>{

}
