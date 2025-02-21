package com.contabia.contabia.models.dto;

public record ChangePasswordDto(String token, String password, String confirmPassword) {

}
