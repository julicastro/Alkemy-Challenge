package com.alkemy.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

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
        if (name != null) {
            model.addAttribute("personajes", personajeService.findByName(name));
        }
        if (age != null) {
            model.addAttribute("personajes", personajeService.findByAge(age));
        }
        if (idMovie != null) {
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
    public String guardar(@Valid Personaje personaje, BindingResult result,
            Model model, @RequestParam("file") MultipartFile imagen, SessionStatus status) {
        status.setComplete();
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Personaje");
            model.addAttribute("botonSubmit", "Crear Personaje");
            
            return "characters-form";
        }
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoulta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] byteImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoulta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, byteImg);

                personaje.setImagen(imagen.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }
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

    // ELIMINAR

    @RequestMapping(value = "/delete-characters/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            personajeService.delete(id);
        }
        return "redirect:/characters";
    }

}
