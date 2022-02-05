package com.alkemy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth/login")
public class LoginController {

    @GetMapping()
    public String login() {
        return "login";
    }

    @PostMapping()
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String mail,
            @RequestParam(required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario y o contraseña incorrecta");
            return "error";
        }
        if (mail != null) {
            model.addAttribute("mail", "Mail incorrecto");
        }
        return "/";
    }

}
