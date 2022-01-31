package com.alkemy.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "peliculas")
public class Pelicula implements Serializable{

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String titulo;

    @Column(name = "create_at") 
    @Temporal(TemporalType.DATE) 
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private Date createAt;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer calificacion;

    public Pelicula() {
    }


    public Pelicula(Long id, String titulo, Date createAt, Integer calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.createAt = createAt;
        this.calificacion = calificacion;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }






}
