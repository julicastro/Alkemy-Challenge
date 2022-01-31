package com.alkemy.service;

import java.util.List;

import com.alkemy.models.entity.Pelicula;

public interface IPeliculaService {
    
    public List <Pelicula> findAll(); 
    public void save (Pelicula pelicula); 
    public Pelicula findOne(Long id); 
    public void delete(Long id);

}
