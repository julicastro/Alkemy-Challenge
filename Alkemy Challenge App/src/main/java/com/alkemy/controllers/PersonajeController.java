package com.alkemy.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
// @RequestMapping("characters")
@SessionAttributes("genero")
public class PersonajeController {

    @Autowired
    private IPersonajeService personajeService;

    @Autowired
    private IPeliculaService peliculaService;
    // LISTAR


    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    public String listar(@RequestParam("name") @Nullable String name, @RequestParam("age") @Nullable Integer age, 
                        @RequestParam("idMovie") @Nullable Long idMovie, Model model) {
        model.addAttribute("titulo", "Detalle de Personaje");
        model.addAttribute("personajes", personajeService.findAll());
        if(name!= null){
            model.addAttribute("personajes", personajeService.findByName(name));   
        }
        if(age!= null){
            model.addAttribute("personajes", personajeService.findByAge(age));
        }
        if(idMovie!= null){
            model.addAttribute("personajes", personajeService.findByMovieId(idMovie));
        }
        return null;
    }

    @ModelAttribute("listarPeliculas")
    public List<Pelicula> listarPeliculas(Model model) {
        return peliculaService.findAll();
    }

    // CREAR

    @RequestMapping(value = "/characters-form")
    public String crear(Map<String, Object> model) {
        Personaje personaje = new Personaje();
        model.put("personaje", personaje);
        model.put("titulo", "Formulario de Personaje");
        model.put("botonSubmit", "Crear Personaje");
        return "characters-form";
    }

    @RequestMapping(value = "/characters-form", method = RequestMethod.POST)
    public String guardar(@Valid Personaje personaje, BindingResult result, Model model, SessionStatus status) {

        status.setComplete();
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Personaje");
            model.addAttribute("botonSubmit", "Crear Personaje");
            return "characters-form";
        }
        personajeService.save(personaje);
        return "redirect:characters";
    }

    // EDITAR

    @RequestMapping(value = "/characters-form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Personaje personaje = null;
        if (id > 0) {
            personaje = personajeService.findOne(id);
        } else {
            return "redirect/characters";
        }
        model.put("personaje", personaje);
        model.put("titulo", "Editar Personaje");
        model.put("botonSubmit", "Guardar");
        return "characters-form";
    }

    // ELIMINAR

    @RequestMapping(value = "/delete-characters/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            personajeService.delete(id);
        }
        return "redirect:/characters";
    }

    
}
