package com.alkemy.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
@Table(name = "generos")
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL )
    @JoinTable(
        name="genero_peliculas", 
        joinColumns = @JoinColumn(name="genero_id"),
        inverseJoinColumns = @JoinColumn(name="perlicula_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"genero_id", "perlicula_id"})
        )
    private List<Pelicula> peliculas;
  
    public Genero (){
        peliculas = new ArrayList<>();
    }

    public Genero(Long id, String nombre, List<Pelicula> peliculas) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.peliculas = peliculas;
    }

}
