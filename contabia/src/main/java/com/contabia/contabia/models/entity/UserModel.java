package com.contabia.contabia.models.entity;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.contabia.contabia.models.dto.EditUserDto;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.enums.ClientRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Classe model responsável por fazer a representação da tabela usuarios do banco de dados.
 * 
 * Atributos:
 * id: identificador da instância
 * cnpj: cnpj do usuario
 * email: email do usuario
 * senha: senha do cadastro no site contabia
 * usuarioSefaz: nome do usuario no site da Secretária da Fazenda - PB
 * senhaSefaz: senha do usuario no site da Secretária da Fazenda - PB
 * 
*/

@NoArgsConstructor
@Getter
@Entity
@Table(name = "usuarios")
public class UserModel extends ClientModel{

    @Column(unique = true, nullable = false)
    private String email;
    
    // Declaração de relação 1:n da entidade usuario com a entidade empresa no banco de dados.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpresaModel> empresas;

    // Construtor com base no UserDto
    public UserModel(UserDto dados){
        super(dados.cnpj(), new BCryptPasswordEncoder().encode(dados.senha()), ClientRole.USER);
        this.email = dados.email();
    }

    public void editUser(EditUserDto dados){
        super.setUsername(dados.cnpj());
        this.email = dados.email();
    }
}
