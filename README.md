# Swagger Converter CLI

**Convert Swagger 2.0 specifications to OpenAPI 3.0 from the command line.**
---

## Prerequisites

- **Java 21+ JDK** (required for Gradle build)
- **GraalVM CE 21+** (with `native-image` component) if building native binaries

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/swagger-converter-cli.git
   cd swagger-converter-cli
   ```

2. **Build the fat‑JAR**
   ```bash
   ./gradlew shadowJar
   # JAR is at build/libs/swagger-converter-cli-1.0.0.jar
   ```

3. **(Optional) Build native binaries**
   ```bash
   # Ensure GraalVM native-image is on your PATH
   ./gradlew nativeCompile
   # Binaries in build/native/nativeCompile/, e.g.: swagger-convert-linux
   ```

## Usage

```bash
# JAR mode (JSON output)
java -jar build/libs/swagger-converter-cli-1.0.0.jar -i swagger-v2.json -o openapi-v3.json

# JAR mode (YAML output)
java -jar build/libs/swagger-converter-cli-1.0.0.jar -i swagger-v2.yaml -f yaml -o openapi-v3.yaml

# Native mode
./build/native/nativeCompile/swagger-convert-linux -i swagger-v2.json -o openapi-v3.json
```

Use `-h` or `--help` for details:
```bash
java -jar build/libs/swagger-converter-cli-1.0.0.jar --help
```

## License

Licensed under the [MIT License](LICENSE). Feel free to use and modify!

