# Smart Selfie Authentication Web Application

A web-based selfie and liveness capture system using Smile Identity SDK. This project provides both a modern web interface and a Java backend for capturing and authenticating selfie images with liveness detection.

## Features

- 📸 **Web Interface** - Browser-based capture using Smile Identity Smart Camera Web SDK v11
- 🎯 **Liveness Detection** - Captures selfie and up to 7 liveness frames
- 🚀 **REST API** - Spark Java backend for processing and submitting to Smile Identity
- 🔒 **Secure Configuration** - Environment-based credential management
- ✅ **Real-time Feedback** - Success/error modal notifications
- 🌐 **CORS Enabled** - Ready for local development and testing

## Project Structure

```
Smart-camera-web-java/
├── .env.example                    # Environment variable template
├── .gitignore                      # Git ignore rules (excludes .env)
├── README.md                       # Project documentation
├── build.gradle                    # Gradle build configuration
├── settings.gradle                 # Gradle settings
├── selfie-capture.html            # Web interface for selfie capture
├── src/
│   └── main/
│       └── java/
│           ├── SmartSelfieAuthentication.java  # CLI example
│           └── SmartSelfieWebServer.java       # REST API server
└── resources/
    └── selfie_base64.txt          # Sample base64 image data
```

## Prerequisites

- Java 8 or higher
- Gradle 7.6 or higher
- Smile Identity account ([Get credentials](https://portal.smileidentity.com/))

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/nnanna217/smile-id-js-sdk-java.git
cd smile-id-js-sdk-java
```

### 2. Configure Environment Variables

Copy the example environment file and add your credentials:

```bash
cp .env.example .env
```

Edit `.env` and add your Smile Identity credentials:

```env
SMILE_PARTNER_ID=your_partner_id_here
SMILE_API_KEY=your_api_key_here
SMILE_SID_SERVER=0  # 0 for sandbox, 1 for production
SERVER_PORT=4567
```

### 3. Build the Project

```bash
./gradlew build
```

### 4. Run the Web Server

```bash
./gradlew run
```

The server will start at `http://localhost:4567`

### 5. Access the Web Interface

Open your browser and navigate to:

```
http://localhost:4567/selfie-capture.html
```

## Usage

### Web Interface

1. Open `http://localhost:4567/selfie-capture.html` in your browser
2. Click the capture button to start the selfie session
3. Follow the on-screen instructions for liveness detection
4. Images are automatically extracted and sent to the backend
5. A success modal appears when submission to Smile Identity is complete

### CLI Example

Run the standalone CLI example:

```bash
./gradlew runSmartSelfie
```

This runs the `SmartSelfieAuthentication.java` example with hardcoded test images.

## API Endpoints

### Health Check

```
GET /health
Response: {"status": "ok"}
```

### Submit Selfie Authentication

```
POST /api/smartselfie/authenticate
Content-Type: application/json

Request Body:
{
  "selfieBase64": "base64_encoded_selfie_image",
  "liveness1Base64": "base64_encoded_liveness_image",
  "liveness2Base64": "base64_encoded_liveness_image",
  ...
  "liveness7Base64": "base64_encoded_liveness_image"
}

Response:
{
  "success": true,
  "message": "Successfully submitted to SmileID",
  "jobId": "unique_job_id",
  "userId": "unique_user_id"
}
```

## Technical Stack

### Backend

- **Java 8** - Core language
- **Smile Identity Core SDK 2.1.6** - Authentication and verification
- **Spark Java 2.9.4** - Lightweight web framework
- **Gson 2.10.1** - JSON serialization
- **Dotenv Java 2.3.2** - Environment variable management
- **Retrofit 2.9.0** - HTTP client
- **Moshi 1.14.0** - JSON parsing

### Frontend

- **Smile Identity Smart Camera Web SDK v11** - Selfie and liveness capture
- **Vanilla JavaScript** - No framework dependencies
- **Bootstrap-style CSS** - Modern UI components

### Build Tools

- **Gradle 7.6** - Build automation

## Dependencies

This project uses:

- Smile Identity Core SDK (2.1.6) - Identity verification
- Spark Java (2.9.4) - REST API framework
- Gson (2.10.1) - JSON processing
- Dotenv Java (2.3.2) - Environment configuration
- Retrofit (2.9.0) - HTTP requests
- Moshi (1.14.0) - JSON parsing
- Jackson (2.12.7.1) - Data binding
- Log4j (2.19.0) - Logging
- SLF4J (2.0.7) - Logging facade

## Configuration

### Environment Variables

| Variable           | Description                                  | Required | Default |
| ------------------ | -------------------------------------------- | -------- | ------- |
| `SMILE_PARTNER_ID` | Your Smile Identity Partner ID               | Yes      | -       |
| `SMILE_API_KEY`    | Your Smile Identity API Key                  | Yes      | -       |
| `SMILE_SID_SERVER` | Server environment (0=sandbox, 1=production) | Yes      | 0       |
| `SERVER_PORT`      | Web server port                              | No       | 4567    |

### Image Types

The system handles the following image types from Smile Identity SDK:

- **Image Type ID 2**: Selfie (base64 encoded)
- **Image Type ID 6**: Liveness frames (base64 encoded)

## Development

### Project Layout

- **selfie-capture.html** - Frontend web interface with Smart Camera Web SDK integration
- **SmartSelfieWebServer.java** - REST API server handling image submissions
- **SmartSelfieAuthentication.java** - Standalone CLI example for testing

### Key Features

1. **Automatic Image Extraction** - JavaScript automatically extracts images from capture event
2. **Dynamic Liveness Handling** - Supports variable number of liveness frames (up to 7)
3. **Base64 Cleaning** - Strips data URI prefixes before backend submission
4. **CORS Support** - Configured for cross-origin requests during development
5. **Error Handling** - Comprehensive error messages and user feedback

## Troubleshooting

### Build Issues

**Problem:** Gradle build fails

```bash
# Clean and rebuild
./gradlew clean build
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is for demonstration purposes using the Smile Identity SDK.

## Resources

- [Smile Identity Documentation](https://docs.usesmileid.com/)
- [Smile Identity Portal](https://portal.smileidentity.com/)
- [Smart Camera Web SDK Documentation](https://docs.usesmileid.com/integration-options/web-mobile-web/javascript-sdk)
- [Spark Java Documentation](https://sparkjava.com/)

## Support

For issues related to:

- **This project**: Open an issue on GitHub
- **Smile Identity SDK**: Contact [Smile Identity Support](https://usesmileid.com/contact)

## Notes

- The `selfie_base64.txt` file contains base64-encoded image data used in the CLI example
- Web server serves static files from project root directory
- Default callback URL can be configured in the source code
- Images are automatically cleaned of data URI prefixes before submission

---

**Built with [Smile Identity](https://usesmileid.com/) SDK**
