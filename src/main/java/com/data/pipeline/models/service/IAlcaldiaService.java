package com.data.pipeline.models.service;

import com.data.pipeline.models.entity.Alcaldia;

import java.util.List;

/**
 * Interface encargada de definir las acciones que se le aplicarán al recurso Alcaldía
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 19, 2020
 */

public interface IAlcaldiaService {

    Alcaldia save(Alcaldia alcaldia);

    List<Alcaldia> findAll();

    Alcaldia findById(Long id);

}
