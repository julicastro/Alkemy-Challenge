package com.alkemy.service;
import java.util.List;

import com.alkemy.models.entity.Personaje;

public interface IPersonajeService {

    public List <Personaje> findAll();
    public void save (Personaje personaje);
    public Personaje findOne(Long id); 
    public void delete(Long id);

    public List <Personaje> findByName(String string);
    public List <Personaje> findByAge(Integer age);
    public List <Personaje> findByMovieId(Long id);

}
