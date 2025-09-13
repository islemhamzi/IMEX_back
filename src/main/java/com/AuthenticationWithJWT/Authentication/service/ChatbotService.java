package com.AuthenticationWithJWT.Authentication.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatbotService {

    // A simple static set of responses
    private static final Map<String, String> responses = new HashMap<>();

    static {
        responses.put("hi", "Hello! How can I help you today?");
        responses.put("bye", "Goodbye! Have a nice day!");
        responses.put("how are you?", "I'm doing great, thanks for asking!");
        responses.put("what is your name?", "I am your friendly chatbot!");
    }

    // Method to get a response based on user input
    public String getResponse(String userMessage) {
        return responses.getOrDefault(userMessage.toLowerCase(), "Sorry, I didn't understand that.");
    }
}
