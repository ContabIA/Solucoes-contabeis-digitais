package com.contabia.contabia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contabia.contabia.infra.ExceptionMessage;
import com.contabia.contabia.models.dto.ChangePasswordDto;
import com.contabia.contabia.services.ResetPasswordService;
import com.contabia.contabia.services.ValidateResetPassTokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/redefinirSenha")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private ValidateResetPassTokenService validatePassTokenService;

    @GetMapping
    public String renderForgotPasswordPage() {
        return "forgotPassword";
    }

    @GetMapping("/novaSenha")
    public String renderChangePasswordPage(@RequestParam String token, Model model) {
        var isValid = validatePassTokenService.isValidToken(token);

        if(isValid){
            return "novaSenha";
        }
        
        return "redirect:/login";
    }

    @PostMapping("/novaSenha")
    public ResponseEntity<ExceptionMessage> changePasswordEndpoint(@RequestBody ChangePasswordDto changePasswordDto, RedirectAttributes redirectAtt) {
        return resetPasswordService.saveNewPassword(changePasswordDto);
    }
    
    @PostMapping
    public String forgotPasswordEndpoint(@RequestParam String userEmail, HttpServletRequest request) {
        resetPasswordService.forgotPassword(userEmail, request);
        return "forgotPassword";
    }
}
