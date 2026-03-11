# Smart Selfie Authentication Web Application

A web-based selfie and liveness capture system using Smile Identity SDK. This project provides both a modern web interface and a Java backend for capturing and authenticating selfie images with liveness detection.

## Features

- 🎨 **Modern UI** - Beautiful purple gradient design with smooth animations
- 👤 **User Enrollment** - Register new users with SmartSelfie biometric capture
- 🔐 **User Authentication** - Verify existing users against enrolled biometric data
- 📸 **Web Interface** - Browser-based capture using Smile Identity Smart Camera Web SDK v11
- 🎯 **Liveness Detection** - Captures selfie and up to 7 liveness frames
- 🚀 **REST API** - Spark Java backend for processing and submitting to Smile Identity
- 🔒 **Secure Configuration** - Environment-based credential management
- ✅ **Real-time Feedback** - Contextual success/error messages based on operation type
- 🌐 **CORS Enabled** - Ready for local development and testing

## Project Structure

```
Smart-camera-web-java/
├── .env.example                    # Environment variable template
├── .gitignore                      # Git ignore rules (excludes .env)
├── README.md                       # Project documentation
├── build.gradle                    # Gradle build configuration
├── settings.gradle                 # Gradle settings
├── index.html                      # Landing page with job type selection
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
http://localhost:4567/index.html
```

## Usage

### Web Interface - User Enrollment

1. Open `http://localhost:4567/index.html` in your browser
2. Select **"Enroll User Using SmartSelfie Registration"** from the dropdown
3. Click **"Continue to Capture"**
4. Follow the on-screen instructions for selfie and liveness capture
5. Images are automatically extracted and sent to the backend
6. On success, you'll see **"User Enrollment Successful!"** with the generated User ID
7. **Save the User ID** - you'll need it for authentication

### Web Interface - User Authentication

1. Open `http://localhost:4567/index.html` in your browser
2. Select **"Authenticate a User with SmartSelfie Auth"** from the dropdown
3. Enter the **User ID** of a previously enrolled user
4. Click **"Continue to Capture"**
5. Complete the selfie and liveness capture
6. On success: **"User Authentication Successful!"**
7. On failure (user not found): **"User Authentication Failed - No enrolled user found."**

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

### Submit Selfie Enrollment/Authentication

```
POST /api/smartselfie/authenticate
Content-Type: application/json

Request Body:
{
  "jobType": "SMART_SELFIE_REGISTRATION or SMART_SELFIE_AUTHENTICATION",
  "userId": "user_id_here (null for registration, required for authentication)",
  "selfieBase64": "base64_encoded_selfie_image",
  "liveness1Base64": "base64_encoded_liveness_image",
  "liveness2Base64": "base64_encoded_liveness_image",
  ...
  "liveness7Base64": "base64_encoded_liveness_image"
}

Success Response:
{
  "success": true,
  "message": "Successfully submitted to SmileID",
  "imageCount": 8,
  "jobId": "web-job-1234567890",
  "userId": "web-user-1234567890"  // Generated for registration, echoed for authentication
}

Error Response:
{
  "success": false,
  "error": "No enrolled user found."  // or other error message
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

### Job Types

The system supports two job types:

- **SMART_SELFIE_REGISTRATION**: Enrolls a new user with biometric data
  - Automatically generates a unique User ID (format: `web-user-{timestamp}`)
  - Creates a new biometric profile in Smile Identity

- **SMART_SELFIE_AUTHENTICATION**: Verifies an existing user
  - Requires a User ID from a previously enrolled user
  - Compares captured biometric data against stored profile

### Image Types

The system handles the following image types from Smile Identity SDK:

- **Image Type ID 2**: Selfie (base64 encoded)
- **Image Type ID 6**: Liveness frames (base64 encoded)

## Development

### Project Layout

- **index.html** - Landing page with job type selection (Registration vs Authentication)
- **selfie-capture.html** - Frontend web interface with Smart Camera Web SDK integration
- **SmartSelfieWebServer.java** - REST API server handling image submissions
- **SmartSelfieAuthentication.java** - Standalone CLI example for testing

### Key Features

1. **Job Type Selection** - User-friendly dropdown to choose between enrollment and authentication
2. **Dynamic User ID Management** - Auto-generated for registration, manual input for authentication
3. **Automatic Image Extraction** - JavaScript automatically extracts images from capture event
4. **Dynamic Liveness Handling** - Supports variable number of liveness frames (up to 7)
5. **Base64 Cleaning** - Strips data URI prefixes before backend submission
6. **CORS Support** - Configured for cross-origin requests during development
7. **Contextual Feedback** - Success/error messages tailored to the operation type
8. **Session Management** - Uses sessionStorage to pass data between pages

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
