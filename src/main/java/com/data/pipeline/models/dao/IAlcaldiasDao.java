package com.data.pipeline.models.dao;

import com.data.pipeline.models.entity.Alcaldia;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface la cual nos permitirá acceder a los datos referentes al objeto Alcaldía
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 19, 2020
 */

public interface IAlcaldiasDao extends CrudRepository<Alcaldia, Long> {
}
