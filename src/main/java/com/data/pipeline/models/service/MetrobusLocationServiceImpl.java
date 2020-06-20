package com.data.pipeline.models.service;

import com.data.pipeline.models.dao.IMetrobusLocationDao;
import com.data.pipeline.models.entity.MetrobusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
