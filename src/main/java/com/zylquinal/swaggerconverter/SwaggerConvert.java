package com.zylquinal.swaggerconverter;

import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import io.swagger.v3.parser.converter.SwaggerConverter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;

import java.nio.file.Files;
import java.nio.file.Paths;

@Command(name = "swagger-convert", mixinStandardHelpOptions = true, version = "1.0",
        description = "Converts Swagger 2.0 specs to OpenAPI 3.0.")
public class SwaggerConvert implements Runnable {

    @Option(names = {"-i", "--input"}, description = "Input Swagger spec file (YAML or JSON)", required = true)
    private String input;

    @Option(names = {"-o", "--output"}, description = "Output OpenAPI spec file (YAML or JSON)")
    private String output;

    @Option(names = {"-f", "--format"}, description = "Output format: json or yaml", defaultValue = "json")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new SwaggerConvert()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            String specText = Files.readString(Paths.get(input));
            SwaggerParseResult result = parseAndConvert(specText);

            OpenAPI openAPI = result.getOpenAPI();
            if (openAPI == null) {
                System.err.println("[ERROR] Conversion failed: " + result.getMessages());
                System.exit(1);
            }
            String outputSpec = serialize(openAPI, format);

            if (output != null) {
                Files.writeString(Paths.get(output), outputSpec);
            } else {
                System.out.println(outputSpec);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Exception during conversion: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static SwaggerParseResult parseAndConvert(String specText) {
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        options.setFlatten(true);
        return new SwaggerConverter().readContents(specText, null, options);
    }

    public static String serialize(OpenAPI openAPI, String format) throws Exception {
        if ("yaml".equalsIgnoreCase(format)) {
            return Yaml.mapper().writeValueAsString(openAPI);
        } else {
            return Json.pretty(openAPI);
        }
    }
}
