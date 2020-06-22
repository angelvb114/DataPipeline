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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcaldias_disponibles").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcaldias_disponibles").isNotEmpty());
    }

}
