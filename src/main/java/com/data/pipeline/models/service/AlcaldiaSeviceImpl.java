package com.data.pipeline.models.service;

import com.data.pipeline.models.dao.IAlcaldiasDao;
import com.data.pipeline.models.entity.Alcaldia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase encargada de implementar las acciones que se llevaran acabo con el recurso Alcaldía
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 19, 2020
 */

@Service
public class AlcaldiaSeviceImpl implements IAlcaldiaService {

    @Autowired
    IAlcaldiasDao iAlcaldiasDao;

    /**
     * Método encargado de guardar en DB los datos del objeto Alcaldía
     *
     * @param alcaldia el objeto con los datos que se van almacenar
     * @return regresa el objeto que fue guardado
     */

    @Override
    @Transactional
    public Alcaldia save(Alcaldia alcaldia) {
        return iAlcaldiasDao.save(alcaldia);
    }

    /**
     * Método encargado de mostrarnos todas las alcaldías en la CDMX
     *
     * @return regresa una lista con alcaldías
     */

    @Override
    @Transactional(readOnly = true)
    public List<Alcaldia> findAll() {
        return (List<Alcaldia>) iAlcaldiasDao.findAll();
    }

    /**
     * Método encargado de buscar una alcaldía por medio del id
     *
     * @param id id de la alcaldía deseada
     * @return regresa los datos de la alcaldía solicitada
     */

    @Override
    @Transactional
    public Alcaldia findById(Long id) {
        return iAlcaldiasDao.findById(id).orElse(null);
    }

}
