package com.alkemy.models.dao;

import com.alkemy.models.entity.Personaje;
import org.springframework.data.repository.CrudRepository;

public interface IPersonajeDao extends CrudRepository<Personaje, Long>{
    

}
