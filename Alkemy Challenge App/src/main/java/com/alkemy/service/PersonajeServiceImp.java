package com.alkemy.service;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.models.entity.Pelicula;
import com.alkemy.models.entity.Personaje;
import com.alkemy.models.dao.IPersonajeDao;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonajeServiceImp implements IPersonajeService {

    @Autowired
    private IPersonajeDao personajeDao;

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> findAll() {
        return (List<Personaje>)personajeDao.findAll();
    }

    @Override
    @Transactional
    public void save(Personaje personaje) {
        personajeDao.save(personaje);
    }

    @Override
    @Transactional(readOnly = true)
    public Personaje findOne(Long id) {
        return personajeDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personajeDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List <Personaje> findByName(String name) {
        List <Personaje> personajesEncontrados = new ArrayList<>();
            for (Personaje p : findAll()) {
                if(p.getNombre().equalsIgnoreCase(name)){
                    personajesEncontrados.add(p);
                }
            }
        return personajesEncontrados;
    }

    @Override
    @Transactional(readOnly = true)
    public List <Personaje> findByAge(Integer age) {
        List <Personaje> personajesEncontrados = new ArrayList<>();
            for (Personaje p : findAll()) {
                if(p.getEdad().equals(age)){
                    personajesEncontrados.add(p);
                }
            }
        return personajesEncontrados;
    }

    @Override
    @Transactional(readOnly = true)
    public List <Personaje> findByMovieId(Long id) {
        List <Personaje> personajesEncontrados = new ArrayList<>();
            for (Personaje p : findAll()) {
                for (Pelicula peli : p.getPeliculas()) {
                    if(peli.getId().equals(id)){
                        personajesEncontrados.add(p);                    
                    }
                }
            }
        return personajesEncontrados;
    }




}
