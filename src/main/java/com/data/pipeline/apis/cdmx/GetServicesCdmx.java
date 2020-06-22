package com.data.pipeline.apis.cdmx;

import com.data.pipeline.models.entity.MetrobusLocation;
import com.data.pipeline.models.service.AlcaldiaSeviceImpl;
import com.data.pipeline.models.service.IAlcaldiaService;
import com.data.pipeline.models.service.IMetrobusLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase esta encargada de realizar la petición a los servicios REST de la CDMX
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 17, 2020
 */

@Slf4j
@Configuration
public class GetServicesCdmx {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SaveAlcaldias saveAlcaldias;

    @Autowired
    private SaveLocationData saveLocationData;

    @Autowired
    private IAlcaldiaService iAlcaldiaService;

    /**
     * Realizamos una petición al servicio que contiene datos de las alcaldías
     *
     * @return usamos un bean de CommandLineRunner para que se ejecute al iniciar la aplicación
     */

    @Bean
    public CommandLineRunner alcaldiasService() {
        return args -> {
            if(iAlcaldiaService.findAll() == null || iAlcaldiaService.findAll().isEmpty()) {
                Map<String, Object> responseJson = restTemplate.getForObject(
                        "https://datos.cdmx.gob.mx/api/records/1.0/search/?dataset=alcaldias&rows=16", HashMap.class);
                if (responseJson != null) {
                    saveAlcaldias.saveInDb(responseJson);
                } else {
                    log.error("The alcaldías service response is null");
                }
            }
        };
    }

    /**
     * Realizamos una petición cada hora de los datos de localización de los metrobus
     */

    @Scheduled(fixedRate = 3600000)
    public void metrobusLocationService() {
        Map<String, Object> responseJson = restTemplate.getForObject(
                "https://datos.cdmx.gob.mx/api/records/1.0/search/?dataset=prueba_fetchdata_metrobus&rows=207", HashMap.class);
        if (responseJson != null) {
            saveLocationData.saveInDb(responseJson);
        } else {
            log.error("The metrobus location service response is null");
        }
    }


}
