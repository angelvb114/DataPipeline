package com.data.pipeline;

import com.data.pipeline.apis.cdmx.SaveLocationData;
import com.data.pipeline.models.entity.MetrobusLocation;
import com.data.pipeline.models.service.IMetrobusLocationService;
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
public class SaveLocationDataTest extends DataPipelineApplicationTests {

    @InjectMocks
    private SaveLocationData saveLocationData;

    @Mock
    private IMetrobusLocationService iMetrobusLocationServiceMock;

    private Map<String, Object> responseMetrobusLocation = new HashMap<>();

    @BeforeEach
    public void setUp() {
        String path = "src/test/resources/ResponseJsonCDMX/MetrobusLocationCDMX.json";
        responseMetrobusLocation = obtainFileLikeMap(path);
    }

    @Test
    public void saveInDbTest() {
        saveLocationData.saveInDb(responseMetrobusLocation);
        Mockito.verify(iMetrobusLocationServiceMock).save(Mockito.any(MetrobusLocation.class));
    }

}
