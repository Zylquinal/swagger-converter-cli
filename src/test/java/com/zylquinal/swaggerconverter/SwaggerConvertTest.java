package com.zylquinal.swaggerconverter;

import io.swagger.util.Json;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SwaggerConvertTest {

    @Test
    void convertPetstoreSwaggerV2FromResource() throws Exception {
        String resourceName = "swagger.json";
        System.out.println("[TEST] Loading Swagger spec from resource: " + resourceName);
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);
        assertNotNull(in, "Resource not found: " + resourceName);
        String specText = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        assertFalse(specText.isEmpty(), "Spec text should not be empty");

        SwaggerParseResult result = SwaggerConvert.parseAndConvert(specText);
        OpenAPI openAPI = result.getOpenAPI();
        System.out.println("[TEST] Conversion messages: " + result.getMessages());
        assertNotNull(openAPI, "OpenAPI model should not be null");
        assertEquals("Swagger Petstore", openAPI.getInfo().getTitle());
        assertTrue(openAPI.getPaths().containsKey("/pet"), "Expected '/pet' path");

        String outputSpec = Json.pretty(openAPI);
        Files.writeString(Path.of("build/swagger-converted.json"), outputSpec);
    }
}