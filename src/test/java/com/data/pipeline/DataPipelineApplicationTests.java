package com.data.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
abstract class DataPipelineApplicationTests {

    protected Map<String, Object> obtainFileLikeMap(String path) {
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = "";
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            content = lines.collect(Collectors.joining(System.lineSeparator()));
            responseMap = objectMapper.readValue(content, HashMap.class);
        } catch (IOException e) {
            log.error("No se pudo obtener la respuesta proveniente del archivo "+path);
        }
        return responseMap;
    }

}
