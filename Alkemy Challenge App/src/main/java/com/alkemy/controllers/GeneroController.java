package com.alkemy.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.alkemy.models.entity.Genero;
import com.alkemy.models.entity.Pelicula;
import com.alkemy.service.IGeneroService;
import com.alkemy.service.IPeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("genero")
public class GeneroController {

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IPeliculaService peliculaService;

    // LISTAR

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Generos de Peliculas");
        model.addAttribute("generos", generoService.findAll());
        return null;
    }

    @ModelAttribute("peliculasAsociadas")
    public List<Pelicula> listarPeliculas(Model model) {
        return peliculaService.findAll();
    }
    
    // CREAR

    @RequestMapping(value = "/genre-form")
    public String crear(Map<String, Object> model) {
        Genero genero = new Genero();
        model.put("genero", genero);
        model.put("titulo", "Formulario de Genero de Peliculas");
        model.put("botonSubmit", "Nuevo Genero");
        return "genre-form";
    }

    @RequestMapping(value = "/genre-form", method = RequestMethod.POST)
    public String guardar(@Valid Genero genero, BindingResult result, Model model, SessionStatus status) {

        status.setComplete();
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Genero de Peliculas");
            model.addAttribute("botonSubmit", "Nuevo Genero");

            return "genre-form";
        }
        generoService.save(genero);
        return "redirect:genre";
    }

    // EDITAR

    @RequestMapping(value = "/genre-form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Genero genero = null;
        if (id > 0) {
            genero = generoService.findOne(id);
        } else {
            return "redirect/genre";
        }
        model.put("genero", genero);
        model.put("titulo", "Editar Generos");
        model.put("botonSubmit", "Guardar");
        return "genre-form";
    }

    // ELIMINAR

    @RequestMapping(value = "/delete-genre/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            generoService.delete(id);
        }
        return "redirect:/genre";
    }

}
