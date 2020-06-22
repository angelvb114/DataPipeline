package com.data.pipeline.models.dao;

import com.data.pipeline.models.entity.MetrobusLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface la cual nos permitirá acceder a los datos referentes al objeto MetrobusLocation
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 20, 2020
 */

public interface IMetrobusLocationDao extends CrudRepository<MetrobusLocation, Long> {

    @Query("select ml from MetrobusLocation ml where ml.unit=?1")
    public List<MetrobusLocation> findByUnitQuery(String unit);

}
