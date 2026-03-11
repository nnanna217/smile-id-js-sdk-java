# Smart Camera Web Java

This project contains the SmartSelfieAuthentication example using the Smile Identity SDK.

## Project Structure

```
Smart-camera-web-java/
├── build.gradle
├── settings.gradle
├── src/
│   └── main/
│       └── java/
│           └── SmartSelfieAuthentication.java
└── resources/
    └── selfie_base64.txt
```

## Prerequisites

- Java 8 or higher
- Gradle 7.6 or higher

## Building the Project

```bash
./gradlew build
```

## Running the Application

```bash
./gradlew run
```

Or use the custom task:

```bash
./gradlew runSmartSelfie
```

## Dependencies

This project uses:
- Smile Identity Core SDK (2.1.6)
- Retrofit for HTTP requests
- Moshi for JSON parsing
- Jackson for data binding
- Log4j for logging

## Note

The `selfie_base64.txt` file contains base64-encoded image data that is loaded at runtime to avoid Java's string literal size limitations.
