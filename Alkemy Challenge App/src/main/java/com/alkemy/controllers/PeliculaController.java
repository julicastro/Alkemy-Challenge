package com.alkemy.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.alkemy.models.entity.Pelicula;
import com.alkemy.models.entity.Personaje;
import com.alkemy.service.IPeliculaService;
import com.alkemy.service.IPersonajeService;

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
@SessionAttributes("pelicula")
public class PeliculaController {

    @Autowired
    private IPeliculaService peliculaService;

    @Autowired IPersonajeService personajeService;

    // LISTAR

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Peliculas");
        model.addAttribute("peliculas", peliculaService.findAll());
        return null;
    }

    @ModelAttribute("listarPersonajes")
    public List<Personaje> listarPersonajes(Model model) {
        return personajeService.findAll();
    }

    // CREAR

    @RequestMapping(value = "/movies-form")
    public String crear(Map<String, Object> model) {
        Pelicula pelicula = new Pelicula();
        model.put("pelicula", pelicula);
        model.put("titulo", "Formulario de pelicula");
        model.put("botonSubmit", "Crear pelicula");
        return "movies-form";
    }

    @RequestMapping(value = "/movies-form", method = RequestMethod.POST)
    public String guardar(@Valid Pelicula pelicula, BindingResult result, Model model, SessionStatus status) {

        status.setComplete();
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de cliente");
            model.addAttribute("botonSubmit", "Crear pelicula");

            return "movies-form";
        }
        peliculaService.save(pelicula);
        return "redirect:movies";
    }

    // EDITAR

    @RequestMapping(value = "/movies-form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Pelicula pelicula = null;
        if (id > 0) {
            pelicula = peliculaService.findOne(id);
        } else {
            return "redirect/movies";
        }
        model.put("pelicula", pelicula);
        model.put("titulo", "Editar Pelicula");
        model.put("botonSubmit", "Guardar");
        return "movies-form";
    }

    // ELIMINAR

    @RequestMapping(value = "/delete-movie/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            peliculaService.delete(id);
        }
        return "redirect:/movies";
    }

}
