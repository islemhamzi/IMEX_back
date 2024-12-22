package com.AuthenticationWithJWT.Authentication.service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Service
public class HuggingFaceService {

    private final WebClient webClient;

    // Inject WebClient via constructor or use the @Value annotation for API URL
    public HuggingFaceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-inference.huggingface.co") // Hugging Face API URL
                .build();
    }

    public Mono<String> getChatbotResponse(String userMessage) {
        return this.webClient.post()
                .uri("/models/gpt-3") // Your specific model endpoint
                .bodyValue("{\"inputs\": \"" + userMessage + "\"}") // Sending the user message in the request body
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.out.println("Error: " + error.getMessage())); // Handling any errors
    }
}
