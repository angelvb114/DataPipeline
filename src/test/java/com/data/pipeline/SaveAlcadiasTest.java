package com.data.pipeline;

import com.data.pipeline.apis.cdmx.SaveAlcaldias;
import com.data.pipeline.models.entity.Alcaldia;
import com.data.pipeline.models.service.IAlcaldiaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Angel Vega
 * @version: 1.0
 * Created on junio 22, 2020
 */

@ExtendWith(MockitoExtension.class)
public class SaveAlcadiasTest extends DataPipelineApplicationTests {

    @InjectMocks
    private SaveAlcaldias saveAlcaldias;

    @Mock
    private IAlcaldiaService iAlcaldiaServiceMock;

    private Map<String, Object> responseAlcaldias = new HashMap<>();

    @BeforeEach
    public void setUp() {
        String path = "src/test/resources/ResponseJsonCDMX/AlcaldiasCDMX.json";
        responseAlcaldias = obtainFileLikeMap(path);
    }

    @Test
    public void saveInDbTest() {
        saveAlcaldias.saveInDb(responseAlcaldias);
        Mockito.verify(iAlcaldiaServiceMock).save(Mockito.any(Alcaldia.class));
    }

}
