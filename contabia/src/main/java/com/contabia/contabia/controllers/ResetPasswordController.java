package com.contabia.contabia.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contabia.contabia.infra.ResponseMessage;
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
    public String renderChangePasswordPage(@RequestParam String token, RedirectAttributes redirectAtt) {
        var isValid = validatePassTokenService.isValidToken(token);

        if(isValid){
            return "novaSenha";
        }
        
        redirectAtt.addFlashAttribute("invalidToken", true);
        return "redirect:/login";
    }

    @PostMapping("/novaSenha")
    public ResponseEntity<ResponseMessage> changePasswordEndpoint(@RequestBody ChangePasswordDto changePasswordDto, RedirectAttributes redirectAtt) {
        return resetPasswordService.saveNewPassword(changePasswordDto);
    }
    
    @PostMapping
    public ResponseEntity<ResponseMessage> forgotPasswordEndpoint(@RequestBody Map<String, String> userEmail, HttpServletRequest request) {
        return resetPasswordService.forgotPassword(userEmail.get("userEmail"), request);
    }
}
