package com.contabia.contabia.models.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reset_senha_token")
public class ResetPasswordToken {
    private static final int EXPIRATION = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    private String token;

    @OneToOne(targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserModel user;

    @Setter
    private Date expiryDate;

    public ResetPasswordToken(UserModel user, String token){
        this.user = user;
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public Date calculateExpiryDate(){
        var nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, EXPIRATION);
        return nowTime.getTime();
    }

}
