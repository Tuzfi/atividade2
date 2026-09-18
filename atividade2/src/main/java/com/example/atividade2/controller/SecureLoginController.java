package com.example.atividade2.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.atividade2.config.UserConfig;
import com.example.atividade2.service.SendEmailService;
import com.example.atividade2.service.UserService;

@Controller
public class SecureLoginController {

    @SuppressWarnings("unused")
    private final UserConfig userConfig;
    private final SendEmailService sendEmailService;
    private final UserService userService;

    public SecureLoginController(
            UserConfig userConfig,
            SendEmailService sendEmailService,
            UserService userService) {

        this.userConfig = userConfig;
        this.sendEmailService = sendEmailService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "home";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("eventDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam("password") String password) {

        // Verifica se o usuário já existe
        if (userService.exists(email)) {
            System.out.println("Usuário já cadastrado: " + email);
            return "redirect:/register";
        }

        // Cria o usuário
        userService.createUser(username, password);

        System.out.println("Usuario Cadastrado: " + username);
        System.out.println("Portador do email: " + email);
        System.out.println("Data do evento: " + eventDate);

        // Redireciona para o login
        return "redirect:/login?cadastro=sucesso";
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverPassword(
            @RequestParam("email") String email) {

        sendEmailService.sendEmail(
                email,
                "Recuperação de Senha",
                "Aqui está o link para recuperar sua senha: [link de recuperação]"
        );

        System.out.println(
                "Recuperação de E-mail: Redirecionado para a página de login."
        );

        return "redirect:/login";
    }
}