package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        logger.info("Received message: {}", message);

        // Validate the message
        if (message == null) {
            logger.error("Received null message");
            return new ChatMessage("System", "Error: Empty message received");
        }

        if (message.getSender() == null || message.getSender().trim().isEmpty()) {
            message.setSender("Anonymous");
        }

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            message.setContent("Empty message");
        }

        logger.info("Sending message: {}", message);
        return message;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}