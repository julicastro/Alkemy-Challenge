package com.alkemy.controllers;


import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import com.alkemy.models.entity.Usuario;
import com.alkemy.service.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/auth/register")
public class RegistroController {
    

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping()
    public String register() {
        return "/register";
    }

    
    @PostMapping()
    public String register(Model model, @ModelAttribute Usuario usuario) throws IOException {
        try {
            usuarioService.create(usuario);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "/register";
        }
    }
    



    


    
}
