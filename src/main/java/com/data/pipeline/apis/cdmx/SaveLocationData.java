package com.data.pipeline.apis.cdmx;

import com.data.pipeline.models.entity.MetrobusLocation;
import com.data.pipeline.models.service.IMetrobusLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase se encarga de guardar los datos de ubicación de las unidades de metrobús en la CDMX
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 18, 2020
 */

@Slf4j
@Component
public class SaveLocationData {

    @Autowired
    private IMetrobusLocationService iMetrobusLocationService;

    public void saveInDb(Map<String, Object> responseJson) {
        if (responseJson.containsKey("records")) {
            List<Map<String, Object>> records = (List<Map<String, Object>>) responseJson.get("records");
            int sizeMetrobusLocation = 0;
            MetrobusLocation lastMetrobusLocation = null;
            if(iMetrobusLocationService.findAll().size() > 0) {
                sizeMetrobusLocation = iMetrobusLocationService.findAll().size();
                lastMetrobusLocation = iMetrobusLocationService.findById((long) (sizeMetrobusLocation));
            }
            MetrobusLocation finalLastMetrobusLocation = lastMetrobusLocation;
            records.forEach(record -> {
                HashMap<String, Object> field = (HashMap<String, Object>) record.get("fields");
                MetrobusLocation metrobusLocation = new MetrobusLocation();
                metrobusLocation.setUnit(field.get("vehicle_id").toString());
                metrobusLocation.setDate(parseDate(field.get("date_updated").toString()));
                metrobusLocation.setLatitude((Double) field.get("position_latitude"));
                metrobusLocation.setLongitude((Double) field.get("position_longitude"));
                if(finalLastMetrobusLocation == null || metrobusLocation.getDate().after(finalLastMetrobusLocation.getDate())) {
                    iMetrobusLocationService.save(metrobusLocation);
                }
            });
        } else {
            log.error("Error: The 'records' tag not was found!!");
        }
    }

    /**
     * Permite convertir un String en fecha (Date).
     *
     * @param fecha Cadena de fecha yyyy-MM-dd HH:mm:ss
     * @return regresa la fecha en objeto Date
     */

    private static Date parseDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            log.info("Error: No se pudo convertir la fecha: ", ex);
        }
        return fechaDate;
    }

}
