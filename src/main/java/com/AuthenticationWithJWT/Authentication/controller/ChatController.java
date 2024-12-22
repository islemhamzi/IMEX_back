package com.AuthenticationWithJWT.Authentication.controller;

import com.AuthenticationWithJWT.Authentication.entities.ChatRequest;
import com.AuthenticationWithJWT.Authentication.entities.MessageRequest;
import com.AuthenticationWithJWT.Authentication.service.ChatClient;
import com.AuthenticationWithJWT.Authentication.service.CohereChatService;
import com.AuthenticationWithJWT.Authentication.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;



import java.util.HashMap;
import java.util.Map;


import com.AuthenticationWithJWT.Authentication.entities.MessageRequest;
import com.AuthenticationWithJWT.Authentication.service.CohereChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final CohereChatService cohereChatService;

    @Autowired
    public ChatController(CohereChatService cohereChatService) {
        this.cohereChatService = cohereChatService;
    }

    // Endpoint to send a message to the chatbot and get a response (both message and response)
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageRequest messageRequest) {
        // Validate the incoming message
        if (messageRequest.getMessage() == null || messageRequest.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
        }

        // Call Cohere API through the service
        try {
            // Send the user's message to Cohere API and get the response
            String botResponse = cohereChatService.getResponseFromCohere(messageRequest.getMessage());

            // Prepare a JSON response with the bot's response
            Map<String, String> response = new HashMap<>();
            response.put("message", botResponse);

            return ResponseEntity.ok(response); // Return the response as a JSON object
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }

}

