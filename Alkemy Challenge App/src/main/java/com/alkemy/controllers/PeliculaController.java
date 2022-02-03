package com.alkemy.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.alkemy.models.entity.Pelicula;
import com.alkemy.models.entity.Personaje;
import com.alkemy.service.IPeliculaService;
import com.alkemy.service.IPersonajeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("pelicula")
public class PeliculaController {

    @Autowired
    private IPeliculaService peliculaService;

    @Autowired
    private IPersonajeService personajeService;

    // LISTAR

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String listar(@RequestParam("name") @Nullable String name, @RequestParam("genreId") @Nullable Long genreId,
            @RequestParam("order") @Nullable String order, Model model) {
        model.addAttribute("titulo", "Lista de Películas");
        model.addAttribute("peliculas", peliculaService.findAll());
        if (name != null) {
            model.addAttribute("peliculas", peliculaService.findByName(name));
        }
        if (genreId != null) {
            model.addAttribute("peliculas", peliculaService.findByGenreId(genreId));
        }
        if (order != null) {
            model.addAttribute("peliculas", peliculaService.orderList(order));
        }
        return null;
    }
    /* devuelvo el tipo de lista filtrada u ordenada segun request param */

    @ModelAttribute("listarPersonajes")
    public List<Personaje> listarPersonajes(Model model) {
        return personajeService.findAll();
    }
    /*
     * llamo a la lista de personajes para poder iterarla en la vista y asi obtener
     * el nombre de cada personaje
     */

    @RequestMapping(value = "/movies-form")
    public String crear(Map<String, Object> model) {
        Pelicula pelicula = new Pelicula();
        model.put("pelicula", pelicula);
        model.put("titulo", "Formulario de Películas");
        model.put("botonSubmit", "Crear película");
        return "movies-form";
    }

    @RequestMapping(value = "/movies-form", method = RequestMethod.POST)
    public String guardar(@Valid Pelicula pelicula, BindingResult result, Model model, SessionStatus status) {

        status.setComplete();
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Películas");
            model.addAttribute("botonSubmit", "Crear película");
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
