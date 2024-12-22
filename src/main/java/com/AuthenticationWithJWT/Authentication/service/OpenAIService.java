package com.AuthenticationWithJWT.Authentication.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String openAiUrl = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare the request body
        String jsonRequest = String.format("{\"model\": \"gpt-3.5-turbo\", \"messages\":[{\"role\":\"user\", \"content\":\"%s\"}]}",
                message);

        // Set the headers for the API call
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the entity and make the API call
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(openAiUrl, HttpMethod.POST, entity, String.class);

        // Extract and return the response message
        return response.getBody();
    }
}
