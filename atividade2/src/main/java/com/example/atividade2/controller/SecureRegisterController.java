package com.example.atividade2.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.atividade2.model.User;
import com.example.atividade2.repository.UserRepository;

@Controller
public class SecureRegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SecureRegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            Model model) {

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("erro", "Preencha todos os campos.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("erro", "As senhas não coincidem.");
            return "register";
        }

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("erro", "Esse nome de usuário já está em uso.");
            return "register";
        }

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("erro", "Esse email já está cadastrado.");
            return "register";
        }

        String senhaCriptografada = passwordEncoder.encode(password);
        User novoUsuario = new User(username, email, senhaCriptografada, birthDate);
        userRepository.save(novoUsuario);

        return "redirect:/login";
    }
}