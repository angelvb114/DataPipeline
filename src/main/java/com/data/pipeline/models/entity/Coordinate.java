package com.data.pipeline.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Esta clase es el modelo de una coordenada en 2d
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 17, 2020
 */

@Data
@Entity
@Table(name = "coordinates")
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private Double latitude;

    @Column(length = 30)
    private Double longitude;

}
