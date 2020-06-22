package com.data.pipeline.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Esta clase es el modelo de los datos de ubicación del Metrobus
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 19, 2020
 */

@Data
@Entity
@Table(name = "metrobus_location")
public class MetrobusLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String unit;

    @Column(length = 30)
    private Date date;

    @Column(length = 30)
    private Double latitude;

    @Column(length = 30)
    private Double longitude;

}
