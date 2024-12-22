package com.AuthenticationWithJWT.Authentication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatClient {

    private static final String COHERE_API_URL = "https://api.cohere.ai/v1/chat";
    private final RestTemplate restTemplate;
    private final String apiKey;

    public ChatClient(@Value("${cohere.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    // Send message method and return message with response
    public String sendMessage(String userMessage) {
        // Construct the request body in JSON format
        String requestBody = "{ \"messages\": [{ \"role\": \"user\", \"content\": \"" + userMessage + "\" }] }";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create an HttpEntity object to hold the request data
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the request to the Cohere API
        ResponseEntity<String> response = restTemplate.exchange(COHERE_API_URL, HttpMethod.POST, entity, String.class);

        // Return a formatted response that includes both the user message and the chatbot's reply
        return formatResponse(userMessage, response.getBody());
    }

    // Format the response
    private String formatResponse(String userMessage, String botResponse) {
        return "{ \"userMessage\": \"" + userMessage + "\", \"botResponse\": " + botResponse + " }";
    }
}
