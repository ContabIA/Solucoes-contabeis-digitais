package com.contabia.contabia.models.entity;

import com.contabia.contabia.models.enums.ClientRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "aplicacoes")
public class ApplicationModel extends ClientModel{

    public ApplicationModel(String username, String senha){
        super(username, senha, ClientRole.USER);
    }    
}
