package com.alkemy.service;

import java.util.List;

import com.alkemy.models.entity.Genero;

public interface IGeneroService {
    
    public List <Genero> findAll(); 
    public void save (Genero genero); 
    public Genero findOne(Long id); 
    public void delete(Long id);

}
