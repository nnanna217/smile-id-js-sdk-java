import static spark.Spark.*;
import com.google.gson.Gson;
import smile.identity.core.WebApi;
import io.github.cdimascio.dotenv.Dotenv;
import smile.identity.core.models.ImageDetail;
import smile.identity.core.models.PartnerParams;
import smile.identity.core.models.Options;
import smile.identity.core.enums.ImageType;
import smile.identity.core.enums.JobType;

import static smile.identity.core.enums.ImageType.LIVENESS_BASE64;

import java.util.*;

public class SmartSelfieWebServer {

    private static final Gson gson = new Gson();
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static void main(String[] args) {
        // Configure port (default 4567)
        port(4567);

        // Serve static files from project root using absolute path
        String projectRoot = System.getProperty("user.dir");
        staticFiles.externalLocation(projectRoot);

        // Enable CORS (uses before() filter, not routes)
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Handle CORS preflight requests
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        // Health check endpoint
        get("/health", (req, res) -> {
            res.type("application/json");
            return "{\"status\": \"ok\"}";
        });

        // Main endpoint to receive selfie and liveness images
        post("/api/smartselfie/authenticate", (req, res) -> {
            res.type("application/json");

            try {
                // Parse incoming JSON
                String body = req.body();
                System.out.println("Received request: " + body.substring(0, Math.min(200, body.length())) + "...");

                ImagePayload payload = gson.fromJson(body, ImagePayload.class);

                // Validate payload
                if (payload.selfieBase64 == null || payload.selfieBase64.isEmpty()) {
                    res.status(400);
                    return gson.toJson(new ErrorResponse("Selfie image is required"));
                }

                // Process with SmileID
                Map<String, Object> result = processWithSmileID(payload);

                res.status(200);
                return gson.toJson(result);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return gson.toJson(new ErrorResponse("Server error: " + e.getMessage()));
            }
        });

        System.out.println("SmartSelfie Web Server started on http://localhost:4567");
        System.out.println("API endpoint: http://localhost:4567/api/smartselfie/authenticate");
        System.out.println("Access HTML page: http://localhost:4567/selfie-capture.html");
    }

    private static Map<String, Object> processWithSmileID(ImagePayload payload) {
        try {
            // Load credentials from environment variables
            String partnerId = dotenv.get("SMILE_PARTNER_ID", "1335");
            String apiKey = dotenv.get("SMILE_API_KEY", "43608984-f548-4fe0-9af2-1a1bec0604a0");
            String defaultCallback = "https://webhook.site/be0a1061-37cc-4eb5-abbe-45faa5795d2c";
            String sidServer = dotenv.get("SMILE_SID_SERVER", "0");  // '0' for sandbox, '1' for production

            // Initialize WebApi
            WebApi connection = new WebApi(partnerId, apiKey, defaultCallback, sidServer);

            // Generate unique IDs
            String timestamp = String.valueOf(System.currentTimeMillis());
            String uniqueUserId = "web-user-" + timestamp;
            String uniqueJobId = "web-job-" + timestamp;

            // Create tracking parameters
            Map<String, Object> optionalInfo = new HashMap<>();
            optionalInfo.put("source", "web-capture");

            PartnerParams params = new PartnerParams(
                JobType.SMART_SELFIE_AUTHENTICATION,
                uniqueUserId,
                uniqueJobId,
                optionalInfo
            );

            // Create image details list
            List<ImageDetail> imageDetails = new ArrayList<>();

            // Add selfie
            ImageDetail selfie = new ImageDetail(ImageType.SELFIE_BASE64, payload.selfieBase64, null);
            imageDetails.add(selfie);

            // Add liveness images (using LIVENESS_BASE64 enum)
            if (payload.liveness1Base64 != null && !payload.liveness1Base64.isEmpty()) {
                ImageDetail liveness1 = new ImageDetail(LIVENESS_BASE64, payload.liveness1Base64, null);
                imageDetails.add(liveness1);
            }
            if (payload.liveness2Base64 != null && !payload.liveness2Base64.isEmpty()) {
                ImageDetail liveness2 = new ImageDetail(LIVENESS_BASE64, payload.liveness2Base64, null);
                imageDetails.add(liveness2);
            }
            if (payload.liveness3Base64 != null && !payload.liveness3Base64.isEmpty()) {
                ImageDetail liveness3 = new ImageDetail(LIVENESS_BASE64, payload.liveness3Base64, null);
                imageDetails.add(liveness3);
            }
            if (payload.liveness4Base64 != null && !payload.liveness4Base64.isEmpty()) {
                ImageDetail liveness4 = new ImageDetail(LIVENESS_BASE64, payload.liveness4Base64, null);
                imageDetails.add(liveness4);
            }
            if (payload.liveness5Base64 != null && !payload.liveness5Base64.isEmpty()) {
                ImageDetail liveness5 = new ImageDetail(LIVENESS_BASE64, payload.liveness5Base64, null);
                imageDetails.add(liveness5);
            }
            if (payload.liveness6Base64 != null && !payload.liveness6Base64.isEmpty()) {
                ImageDetail liveness6 = new ImageDetail(LIVENESS_BASE64, payload.liveness6Base64, null);
                imageDetails.add(liveness6);
            }
            if (payload.liveness7Base64 != null && !payload.liveness7Base64.isEmpty()) {
                ImageDetail liveness7 = new ImageDetail(LIVENESS_BASE64, payload.liveness7Base64, null);
                imageDetails.add(liveness7);
            }

            System.out.println("Processing " + imageDetails.size() + " images with SmileID");

            // Set up options
            boolean returnJobStatus = true;
            boolean returnHistory = false;
            boolean returnImageLinks = false;
            String callbackUrl = null;  // Use default callback

            Options options = new Options(returnHistory, returnImageLinks, returnJobStatus, callbackUrl);

            // Submit job to SmileID
            System.out.println("Submitting job to SmileID...");
            connection.submitJob(params, imageDetails, null, options);
            System.out.println("Job submitted successfully!");

            // Create success response
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Successfully submitted to SmileID");
            result.put("imageCount", imageDetails.size());
            result.put("userId", uniqueUserId);
            result.put("jobId", uniqueJobId);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }

    // Data classes
    static class ImagePayload {
        String selfieBase64;
        String liveness1Base64;
        String liveness2Base64;
        String liveness3Base64;
        String liveness4Base64;
        String liveness5Base64;
        String liveness6Base64;
        String liveness7Base64;
    }

    static class ErrorResponse {
        String error;

        ErrorResponse(String error) {
            this.error = error;
        }
    }
}
