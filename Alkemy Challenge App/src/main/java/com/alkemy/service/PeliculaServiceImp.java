package com.alkemy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alkemy.models.dao.IGeneroDao;
import com.alkemy.models.dao.IPeliculaDao;
import com.alkemy.models.entity.Genero;
import com.alkemy.models.entity.Pelicula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeliculaServiceImp implements IPeliculaService {

    @Autowired
    private IPeliculaDao peliculaDao;

    @Autowired
    private IGeneroDao generoDao;

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

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> findByName(String name) {
        List<Pelicula> pelicularEncontradas = new ArrayList<>();
        for (Pelicula p : findAll()) {
            if (p.getTitulo().equals(name)) {
                pelicularEncontradas.add(p);
            }
        }
        return pelicularEncontradas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> findByGenreId(Long id) {
        List<Pelicula> pelicularEncontradas = new ArrayList<>();
        for (Genero g : generoDao.findAll()) {
            for (Pelicula p : g.getPeliculas()) {
                if (p.getId().equals(id)) {
                    pelicularEncontradas.add(p);
                }
            }
        }
        return pelicularEncontradas;
    }

    @Override
    public List<Pelicula> orderList(String orden) {
        List<Pelicula> listaOrdenada;
        listaOrdenada = findAll();
        if (orden.equalsIgnoreCase("asc")) {
            Collections.sort(listaOrdenada);
        } else if (orden.equalsIgnoreCase("desc")) {
            Collections.sort(listaOrdenada, Collections.reverseOrder());
        }
        return listaOrdenada;

    }

}
