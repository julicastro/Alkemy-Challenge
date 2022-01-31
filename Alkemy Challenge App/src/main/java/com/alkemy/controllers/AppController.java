package com.alkemy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "Inicio");
        return "inicio";
    }
    

}
