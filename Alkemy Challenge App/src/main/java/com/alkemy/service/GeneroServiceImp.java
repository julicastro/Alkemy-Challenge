package com.alkemy.service;

import java.util.List;

import com.alkemy.models.dao.IGeneroDao;
import com.alkemy.models.entity.Genero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneroServiceImp implements IGeneroService {

    @Autowired
    private IGeneroDao generoDao;

    @Override
    @Transactional(readOnly = true) 
    public List<Genero> findAll() {
        return (List<Genero>) generoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Genero genero) {
        generoDao.save(genero);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Genero findOne(Long id) {
        return generoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        generoDao.deleteById(id);
        
    }
    
    
}
