package com.data.pipeline.controllers;

import com.data.pipeline.models.entity.Alcaldia;
import com.data.pipeline.models.entity.Coordinate;
import com.data.pipeline.models.entity.MetrobusLocation;
import com.data.pipeline.models.service.IAlcaldiaService;
import com.data.pipeline.models.service.IMetrobusLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.Path2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Esta clase esta encargada de exponer los servicios que brindan información
 * de la ubicación de las unidades del metrobus
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 20, 2020
 */

@Slf4j
@RestController
@RequestMapping("/metrobus/location")
public class MetrobusLocationRestController {

    @Autowired
    private IMetrobusLocationService iMetrobusLocationService;

    @Autowired
    private IAlcaldiaService iAlcaldiaService;

    /**
     * Servicio encargado de mostrar todas las unidades disponibles del metrobus
     *
     * @return regresa una lista con los id de las unidades de metrobus
     */

    @GetMapping("/units")
    public ResponseEntity<?> showUnits() {

        Map<String, Object> response = new HashMap<>();
        List<MetrobusLocation> metrobusLocationList = iMetrobusLocationService.findAll();
        Set<Map<String, String>> metrobusUnits = metrobusLocationList.stream()
                .map(metrobusLocation -> {
                    Map<String, String> unitMap = new HashMap<>();
                    unitMap.put("unidad_id", metrobusLocation.getUnit());
                    return unitMap;
                })
                .collect(Collectors.toSet());
        response.put("total de unidades", metrobusUnits.size());
        response.put("unidades_disponibles", metrobusUnits);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Servicio encargado de buscar los datos históricos de una unidades de metrobus en especifico
     *
     * @param unit es el id de la unidad de metrobus
     * @return regresa una lista con los datos de ubicación históricos
     */

    @GetMapping("/units/{unit}")
    public ResponseEntity<?> unitLocation(@PathVariable Long unit) {

        Map<String, Object> response = new HashMap<>();
        List<MetrobusLocation> metrobusLocationList;
        try {
            metrobusLocationList = iMetrobusLocationService.findByUnit(unit.toString());
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (metrobusLocationList == null || metrobusLocationList.isEmpty()) {
            response.put("message", "La unidad con ID: ".concat(unit.toString().concat(" no existe en la base de datos!")));
            response.put("error", "Unidad no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Map<String, Object>> historyLocation = metrobusLocationList.stream().map(metrobusLocation -> {
            Map<String, Object> locationData = new HashMap<>();
            locationData.put("date", metrobusLocation.getDate());
            locationData.put("latitud", metrobusLocation.getLatitude());
            locationData.put("longitud", metrobusLocation.getLongitude());
            return locationData;
        }).collect(Collectors.toList());
        response.put("unidad", unit);
        response.put("total_registros", historyLocation.size());
        response.put("history_data", historyLocation);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/alcaldias")
    public ResponseEntity<?> showAlcaldias() {

        Map<String, Object> response = new HashMap<>();
        List<Alcaldia> alcaldiaList = iAlcaldiaService.findAll();
        if(alcaldiaList == null || alcaldiaList.isEmpty()) {
            response.put("message", "No hay alcaldías disponibles en este momento!");
            response.put("error", "Alcaldías no encontradas");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Map<String,Object>> responseLitAlcaldias = alcaldiaList.stream().map(alcaldia -> {
            Map<String, Object> internalMap = new HashMap<>();
            internalMap.put("id", alcaldia.getId());
            internalMap.put("name", alcaldia.getName());
            return internalMap;
        }).collect(Collectors.toList());
        response.put("alcaldias_disponibles", responseLitAlcaldias);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/alcaldias/{id}")
    public ResponseEntity<?> unitAlcaldia(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Alcaldia alcaldia = null;
        try {
            alcaldia = iAlcaldiaService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (alcaldia == null) {
            response.put("message", "La Alcaldía con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            response.put("error", "Alcaldia no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        List<Coordinate> coordinateList = alcaldia.getCoordinateList();
        List<Coordinate> coordinateListFilter = coordinateList.stream()
                .filter(coordinate -> coordinate.getLatitude() != 0 || coordinate.getLongitude() != 0)
                .collect(Collectors.toList());
        Path2D alcaldia2D = new Path2D.Double();
        alcaldia2D.moveTo(coordinateList.get(0).getLatitude(), coordinateList.get(0).getLongitude());
        IntStream.range(1, coordinateListFilter.size()).forEach(indx -> {
            alcaldia2D.lineTo(coordinateListFilter.get(indx).getLatitude(),
                    coordinateListFilter.get(indx).getLongitude());
        });
        alcaldia2D.closePath();
        List<MetrobusLocation> metrobusLocationList = iMetrobusLocationService.findAll();
        List<MetrobusLocation> metrobusLocationListFilter = metrobusLocationList.stream()
                .filter(metrobusLocation -> metrobusLocation.getLatitude() != 0 || metrobusLocation.getLongitude() != 0)
                .collect(Collectors.toList());
        List<MetrobusLocation> metrobusLocationListAlcaldia = metrobusLocationListFilter.stream()
                .filter( metrobusLocation -> alcaldia2D.contains(metrobusLocation.getLatitude(),metrobusLocation.getLongitude()))
                .collect(Collectors.toList());
        if (metrobusLocationListAlcaldia.isEmpty()) {
            response.put("message", "No se encontraron unidades en "+alcaldia.getName()+"!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put(alcaldia.getName(),metrobusLocationListAlcaldia);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
