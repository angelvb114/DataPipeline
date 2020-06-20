package com.data.pipeline.models.service;

import com.data.pipeline.models.entity.MetrobusLocation;

/**
 * Interface encargada de definir las acciones que se le aplicarán al recurso MetrobusLocation
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 20, 2020
 */

public interface IMetrobusLocationService {
    MetrobusLocation save(MetrobusLocation metrobusLocation);
}
