package com.contabia.contabia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contabia.contabia.models.entity.ResetPasswordToken;
import com.contabia.contabia.models.entity.UserModel;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken ,Long>{
    Optional<ResetPasswordToken> findByToken(String token);
    Optional<ResetPasswordToken> findByUser(UserModel user);
}
