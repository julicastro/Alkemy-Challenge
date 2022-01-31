package com.alkemy.models.dao;

import com.alkemy.models.entity.Pelicula;

import org.springframework.data.repository.CrudRepository;

public interface IPeliculaDao extends CrudRepository<Pelicula, Long>{
    
}
