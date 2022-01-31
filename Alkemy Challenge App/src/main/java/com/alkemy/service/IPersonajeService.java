package com.alkemy.service;
import java.util.List;

import com.alkemy.models.entity.Personaje;

public interface IPersonajeService {

    public List <Personaje> findAll();
    public void save (Personaje personaje);
    public Personaje findOne(Long id); 
    public void delete(Long id);


}
