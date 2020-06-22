package com.data.pipeline.models.service;

import com.data.pipeline.models.dao.IMetrobusLocationDao;
import com.data.pipeline.models.entity.MetrobusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase encargada de implementar las acciones que se llevaran acabo con el recurso MetrobusLocation
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 20, 2020
 */

@Service
public class MetrobusLocationServiceImpl implements IMetrobusLocationService {

    @Autowired
    private IMetrobusLocationDao iMetrobusLocationDao;

    /**
     * Método encargado de guardar en DB los datos del objeto MetrobusLocation
     *
     * @param metrobusLocation el objeto con los datos que se van almacenar
     * @return regresa el objeto que fue guardado
     */

    @Override
    @Transactional
    public MetrobusLocation save(MetrobusLocation metrobusLocation) {
        return iMetrobusLocationDao.save(metrobusLocation);
    }

    /**
     * Método encargado de regresar los datos de ubicación de las distintas unidades de metrobus
     *
     * @return una lista con las ubicaciones de las unidades
     */

    @Override
    @Transactional(readOnly = true)
    public List<MetrobusLocation> findAll() {
        return (List<MetrobusLocation>) iMetrobusLocationDao.findAll();
    }

    /**
     * Método encargado de regresar los datos de ubicación de una unidad en especifico
     *
     * @param id de la unidad que se desea buscar
     * @return regresa un objeto con los datos de la unidad
     */

    @Override
    @Transactional
    public MetrobusLocation findById(Long id) {
        return iMetrobusLocationDao.findById(id).orElse(null);
    }

    /**
     * Método encargado de regresar los datos históricos de ubicación de una unidad de metrobus
     *
     * @param unit la unidad deseada
     * @return regresa una lista de datos de ubicacion
     */

    @Override
    @Transactional
    public List<MetrobusLocation> findByUnit(String unit) {
        return iMetrobusLocationDao.findByUnitQuery(unit);
    }

}
