package com.data.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase encargada de probar los servicios rest
 *
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 22, 2020
 */

@Slf4j
@DisplayName("Test with Mock MVC")
public class RestTest extends DataPipelineApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getFindAllAlcaldiasTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/metrobus/location/alcaldias").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcaldias_disponibles").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcaldias_disponibles").isNotEmpty());
    }

    @Test
    public void getFindAllMetrobusLocationTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/metrobus/location/units").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.unidades_disponibles").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.unidades_disponibles").isNotEmpty());
    }

    @Test
    public void getFindMetrobusLocationByAlcaldiaTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/metrobus/location/alcaldias/6").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Iztacalco").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Iztacalco").isNotEmpty());
    }

    @Test
    public void getFindLocationDataByUnitTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/metrobus/location/units/170").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.history_data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.history_data").isNotEmpty());
    }

}
