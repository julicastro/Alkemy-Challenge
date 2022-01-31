package com.alkemy.service;

import java.util.List;
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




}
