package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<MessageResponseDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public MessageResponseDTO getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @PostMapping
    public MessageResponseDTO saveMessage(@RequestBody @Valid Message message) {
        return messageService.saveMessage(message);
    }
}
