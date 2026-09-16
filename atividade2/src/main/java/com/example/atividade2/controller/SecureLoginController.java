package com.example.atividade2.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.atividade2.config.UserConfig;
import com.example.atividade2.service.SendEmailService;
import com.example.atividade2.service.UserService;

@Controller
public class SecureLoginController {

    private final UserConfig userConfig;
    private final SendEmailService sendEmailService;

    public SecureLoginController(UserConfig userConfig, SendEmailService SendEmailService) {
        this.userConfig = userConfig;
        this.sendEmailService = SendEmailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin() {
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
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
            @RequestParam("password") String password) {

        // Aqui você pode adicionar lógica para salvar os dados do usuário, por exemplo:
        // userService.saveUser(new User(nome, email, cpf, rg, endereco, instituicao,
        // senha));
        if (userService.exists(email)) {
            System.out.println("Usuário já cadastrado: " + email);
            return "redirect:/register";
        }

        userService.createUser(email, password);

        System.out.println("Usuário cadastrado: " + email);

        return "redirect:/login?cadastro=sucesso";
    }

    System.out.println("Registro: Redirecionado para a página de login.");return"redirect:/login"; // Após o registro,
                                                                                                   // redirecionar para
                                                                                                   // a página de login

    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverPassword(
            @RequestParam("email") String email) {

        sendEmailService.sendEmail(email, "Recuperação de Senha",
                "Aqui está o link para recuperar sua senha: [link de recuperação]");

        System.out.println("Recuperação de E-mail: Redirecionado para a página de login.");
        return "redirect:/login";
    }
}
