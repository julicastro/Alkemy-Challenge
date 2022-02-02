package com.alkemy.service;

import java.util.List;

import com.alkemy.models.entity.Pelicula;

public interface IPeliculaService {
    
    public List <Pelicula> findAll(); 
    public void save (Pelicula pelicula); 
    public Pelicula findOne(Long id); 
    public void delete(Long id);

    public List <Pelicula> findByName(String string);
    public List <Pelicula> findByGenreId(Long id);

    public List <Pelicula> orderList(String orden); 


}
