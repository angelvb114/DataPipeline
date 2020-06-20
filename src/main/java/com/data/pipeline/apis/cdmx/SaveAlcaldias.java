package com.data.pipeline.apis.cdmx;

import com.data.pipeline.models.entity.Alcaldia;
import com.data.pipeline.models.entity.Coordinate;
import com.data.pipeline.models.service.IAlcaldiaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase se encarga de guardar los datos de ubicación de las distintas alcaldías de la CDMX
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 18, 2020
 */

@Slf4j
@Component
public class SaveAlcaldias {

    @Autowired
    private IAlcaldiaService iAlcaldiaService;

    /**
     * Método encargado de filtrar los parámetros importantes de cada alcaldía y almacenarlo en DB
     *
     * @param responseJson contiene la respuesta del servicio de alcaldías de la CDMX
     */

    protected void saveInDb(Map<String, Object> responseJson) {
        if (responseJson.containsKey("records")) {
            List<Map<String, Object>> records = (List<Map<String, Object>>) responseJson.get("records");
            records.forEach(record -> {
                HashMap<String, Object> field = (HashMap<String, Object>) record.get("fields");
                HashMap<String, Object> geoShape = (HashMap<String, Object>) field.get("geo_shape");
                List<List<List<Double>>> coordinates = (List<List<List<Double>>>) geoShape.get("coordinates");
                Alcaldia alcaldia = new Alcaldia();
                alcaldia.setName(field.get("nomgeo").toString());
                List<Coordinate> coordenadasList = saveCoordenadas(coordinates);
                alcaldia.setCoordinateList(coordenadasList);
                iAlcaldiaService.save(alcaldia);
            });
        } else {
            log.error("Error: The 'records' tag not was found!!");
        }
    }

    /**
     * Método encargado de ordenar las coordenadas por latitud y longitud
     *
     * @param coordinates son las coordenadas en el formato que maneja el api de la CDMX
     * @return regresa una lista del objeto Coordenadas que tiene un formato latitud-longitud
     */

    private List<Coordinate> saveCoordenadas(List<List<List<Double>>> coordinates) {
        List<Coordinate> coordenadasList = new ArrayList<>();
        coordinates.forEach(internalList -> {
            internalList.forEach(point2d -> {
                Coordinate coordinate = new Coordinate();
                coordinate.setLatitude(point2d.get(1));
                coordinate.setLongitude(point2d.get(0));
                coordenadasList.add(coordinate);
            });
        });
        return coordenadasList;
    }

}
