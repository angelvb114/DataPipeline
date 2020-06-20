package com.data.pipeline.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase es el modelo de los componentes de una alcaldía
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 17, 2020
 */

@Data
@Entity
@Table(name = "alcaldias")
public class Alcaldia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "alcaldia_id")
    private List<Coordinate> coordinateList;

    public Alcaldia() {
        coordinateList = new ArrayList<>();
    }
}
