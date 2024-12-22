package com.AuthenticationWithJWT.Authentication.controller;

import com.AuthenticationWithJWT.Authentication.service.DialogGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DialogGPTController {

    private final DialogGPTService dialogGPTService;

    @Autowired
    public DialogGPTController(DialogGPTService dialogGPTService) {
        this.dialogGPTService = dialogGPTService;
    }

    @PostMapping("/api/chat")
    public String getChatResponse(@RequestBody String message) {
        return dialogGPTService.getResponseFromDialogGPT(message);
    }
}
