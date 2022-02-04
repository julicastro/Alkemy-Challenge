package com.alkemy.controllers;

import java.util.ArrayList;
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
            @RequestParam("idMovie") @Nullable Long idMovie, @RequestParam("search") @Nullable String search,
            Model model) {
        model.addAttribute("titulo", "Lista de Personajes");
        model.addAttribute("personajes", personajeService.findAll());
        if (search != null) {
            List<Personaje> personajesEncontrados = new ArrayList<>();
            personajesEncontrados.addAll(personajeService.findByName(search.toString()));
            try {
                personajesEncontrados.addAll(personajeService.findByAge(Integer.parseInt(search)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                personajesEncontrados.addAll(personajeService.findByMovieId(Long.parseLong(search)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("personajes", personajesEncontrados);
        } else {
            if (name != null) {
                model.addAttribute("personajes", personajeService.findByName(name));
            }
            if (age != null) {
                model.addAttribute("personajes", personajeService.findByAge(age));
            }
            if (idMovie != null) {
                model.addAttribute("personajes", personajeService.findByMovieId(idMovie));
            }
        }
        return null;
    }

    /*
     * este método se encarga de listar todos los personajes. puede filtrar por
     * nombre, id de película y edad mostrando los endpoints pedidos en la consigna.
     * Además, le agregué un input que tambien perfite filtrar por estos 3
     * parametros pero cuyo input es /characters?search=value. Para eso recivo un
     * String como Request Param el cual casteo a Integer o Long según corresponda y
     * luego con ese valor llamar a los métodos de PersonajeServiceImp
     * correspondientes
     */

    @ModelAttribute("listarPeliculas")
    public List<Pelicula> listarPeliculas(Model model) {
        return peliculaService.findAll();
    }

    /*
     * obtiene la lista de peliculas para poder iterarlas y mostrarlas en el
     * formulario de creación o edicón de personajes
     */

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

    @RequestMapping(value = "/character-details/{id}")
    public String detalles(@PathVariable(value = "id") Long id, Map<String, Object> model) {

        Personaje personaje = null;
        if (id > 0) {
            personaje = personajeService.findOne(id);
        } else {
            return "redirect/characters";
        }
        model.put("id", personaje.getId());
        model.put("nombre", personaje.getNombre());
        model.put("peso", personaje.getPeso());
        model.put("edad", personaje.getEdad());
        model.put("historia", personaje.getHistoria());
        model.put("peliculas", personaje.getPeliculas());

        model.put("titulo", "Detalles del personaje");
        return "character-details";
    }

}
