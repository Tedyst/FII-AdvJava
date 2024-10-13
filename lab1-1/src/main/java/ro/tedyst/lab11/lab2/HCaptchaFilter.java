package ro.tedyst.lab11.lab2;

import javax.json.Json;
import javax.json.JsonReader;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebFilter(urlPatterns = {"/file-upload"})
public class HCaptchaFilter implements Filter {
    private String secretKey = "ES_c0690a790094402abf77aeaa0d7915da"; // Replace with your secret key

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the request is a POST and targets a specific form submission
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String hCaptchaResponse = httpRequest.getParameter("h-captcha-response");
            String remoteIP = httpRequest.getRemoteAddr();

            // Validate hCAPTCHA
            if (!validateHcaptcha(hCaptchaResponse, remoteIP)) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "hCAPTCHA validation failed.");
                return;
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    private boolean validateHcaptcha(String response, String remoteIP) throws IOException {
        String validationUrl = "https://hcaptcha.com/siteverify";
        String params = "secret=" + secretKey + "&response=" + response + "&remoteip=" + remoteIP;

        URL url = new URL(validationUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }

            // Parse the JSON response
            JsonReader jsonResponse = Json.createReader(new StringReader(responseBuilder.toString()));
            return jsonResponse.readObject().getBoolean("success");
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic, if needed
    }
}
