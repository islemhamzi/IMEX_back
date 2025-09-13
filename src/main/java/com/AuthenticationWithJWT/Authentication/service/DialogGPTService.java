package com.AuthenticationWithJWT.Authentication.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class DialogGPTService {

    private static final String API_URL = "https://api-inference.huggingface.co/models/microsoft/DialoGPT-medium";
    private static final String BEARER_TOKEN = "YOUR_HUGGINGFACE_API_TOKEN";  // Replace with your Hugging Face API token

    private final RestTemplate restTemplate;

    public DialogGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponseFromDialogGPT(String userMessage) {
        // Set up headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + BEARER_TOKEN);

        // Create request body
        String body = "{ \"inputs\": \"" + userMessage + "\" }";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Send POST request to Hugging Face API
        ResponseEntity<String> response = restTemplate.exchange(
                API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
