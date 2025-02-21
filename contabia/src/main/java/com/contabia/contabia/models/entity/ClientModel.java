package com.contabia.contabia.models.entity;

import java.io.Serializable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.contabia.contabia.models.enums.ClientRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy =  InheritanceType.JOINED)
@EqualsAndHashCode(of = "id")
@Table(name = "cliente")
public abstract class ClientModel implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String senha;

    @Column(unique = false, nullable = false)
    private ClientRole role;
    
    protected ClientModel(String username, String senha, ClientRole role){
        this.username = username;
        this.senha = senha;
        this.role = role;
    }

    public void updatePassword(String newPassword){
        this.senha = new BCryptPasswordEncoder().encode(newPassword);
    }
}