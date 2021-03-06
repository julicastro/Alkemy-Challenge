package com.alkemy.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name = "personajes")
public class Personaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @NotNull
    private Integer edad;

    @NotNull
    private Double peso;

    @NotEmpty
    @Size(min = 10, max = 225)
    private String historia;

    // @NotEmpty
    // private String imagen;

    @ManyToMany(cascade = CascadeType.ALL )
    @JoinTable(
        name="personajes_peliculas", 
        joinColumns = @JoinColumn(name="personaje_id"),
        inverseJoinColumns = @JoinColumn(name="perlicula_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"personaje_id", "perlicula_id"})
        )
    private List <Pelicula> peliculas;



    


 

}
