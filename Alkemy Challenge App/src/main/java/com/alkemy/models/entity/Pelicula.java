package com.alkemy.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Entity
@Data
@Table(name = "peliculas")
public class Pelicula implements Serializable, Comparable<Pelicula> {

    private static final long serialVersionUID = 1L;

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

    @ManyToMany(mappedBy = "peliculas")
    List<Personaje> personajes;

    private String imagen;

    @Override
    public int compareTo(Pelicula p) {
        return titulo.compareTo(p.getTitulo());
    }

    @ManyToMany(mappedBy = "peliculas")
    private List<Genero> genero;

    /*
     * Si bien lo correcto sería hacer una relación @OneToMany con el Many del lado
     * de Pelicula, opté por hacerlo @ManyToMany ya que me tiraba un error cuando
     * quería agregar la misma película a 2 o más géneros distintos. Tenía un
     * problema con los ID duplicados.
     */

}