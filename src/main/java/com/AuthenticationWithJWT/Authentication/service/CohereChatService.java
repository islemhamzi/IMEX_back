package com.AuthenticationWithJWT.Authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@Service
public class CohereChatService {

    @Value("${cohere.api.key}")
    private String apiKey; // Your API key from the application.properties

    private static final String COHERE_API_URL = "https://api.cohere.ai/v1/chat"; // Adjusted the correct URL

    private final RestTemplate restTemplate;

    public CohereChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponseFromCohere(String userMessage) {
        String jsonRequestBody = "{"
                + "\"message\": \"" + userMessage + "\","
                + "\"max_tokens\": 50"
                + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);

        // Make the API request
        ResponseEntity<String> response = restTemplate.exchange(
                COHERE_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Parse the response to get the chatbot's message
        String responseBody = response.getBody();
        if (responseBody != null) {
            // Extract the text from the JSON response
            // Use a simple JSON parser like Jackson (ObjectMapper) to parse the response
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(responseBody);
                return responseJson.path("text").asText(); // Extract the 'text' field from the response
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "Error processing response from Cohere API.";
            }
        }
        return "No response from Cohere API.";
    }

}
