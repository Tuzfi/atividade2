package com.example.atividade2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class SecureRegisterController {

     @GetMapping("/register")
        public String register(){
            return "register";
        }
}
