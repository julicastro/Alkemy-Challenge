package com.alkemy.service;

import java.util.List;

import com.alkemy.models.dao.IPeliculaDao;
import com.alkemy.models.entity.Pelicula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeliculaServiceImp implements IPeliculaService {

    @Autowired
    private IPeliculaDao peliculaDao;

    @Override
    @Transactional(readOnly = true) 
    public List<Pelicula> findAll() {
        return (List<Pelicula>) peliculaDao.findAll();
    }

    @Override
    @Transactional
    public void save(Pelicula pelicula) {
        peliculaDao.save(pelicula);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Pelicula findOne(Long id) {
        return peliculaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        peliculaDao.deleteById(id);
        
    }
    
    
}
