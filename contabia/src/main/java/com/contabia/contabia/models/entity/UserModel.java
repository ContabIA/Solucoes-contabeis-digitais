package com.contabia.contabia.models.entity;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.contabia.contabia.models.dto.EditUserDto;
import com.contabia.contabia.models.dto.UserDto;
import com.contabia.contabia.models.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usuarios")
public class UserModel{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = false, nullable = false)
    private String senha;

    @Column(unique = false, nullable = false)
    private String senhaSefaz;

    @Column(unique = true, nullable = false)
    private String userSefaz;

    @Column(unique = false, nullable = false)
    private UserRole role;
    
    // Declaração de relação 1:n da entidade usuario com a entidade empresa no banco de dados.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpresaModel> empresas;

    // Construtor com base no UserDto
    public UserModel(UserDto dados){
        this.cnpj = dados.cnpj();
        this.email = dados.email();
        this.senha = new BCryptPasswordEncoder().encode(dados.senha());
        this.senhaSefaz = dados.senhaSefaz();
        this.userSefaz = dados.userSefaz();
        this.role = UserRole.USER;
    }

    public void editUser(EditUserDto dados){
        this.cnpj = dados.cnpj();
        this.email = dados.email();
        this.senhaSefaz = dados.senhaSefaz();
        this.userSefaz = dados.userSefaz(); 
    }
}
