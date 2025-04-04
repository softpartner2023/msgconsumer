package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {

    private MessageRepository messageRepository;
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MessageRepository.class);
        messageService = new MessageServiceImpl(messageRepository);
    }

    @Test
    void shouldReturnAllMessages() {
        Message msg1 = new Message(null, "Hello", "App1", LocalDateTime.now());
        Message msg2 = new Message(null, "World", "App2", LocalDateTime.now());

        when(messageRepository.findAll()).thenReturn(Arrays.asList(msg1, msg2));

        var messages = messageService.getAllMessages();
        assertEquals(2, messages.size());
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnMessageById() {
        Message msg = new Message(1L, "Test", "AppX", LocalDateTime.now());

        when(messageRepository.findById(1L)).thenReturn(Optional.of(msg));

        MessageResponseDTO result = messageService.getMessageById(1L);
        assertEquals("Test", result.getContent());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenMessageNotFound() {
        when(messageRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException  exception = assertThrows(ResourceNotFoundException.class,
                () -> messageService.getMessageById(99L));

        assertTrue(exception.getMessage().contains("Message not found"));
    }

    @Test
    void shouldSaveMessage() {
        Message input = new Message(null, "Test MQ", "AppY", LocalDateTime.now());
        Message saved = new Message(10L, "Test MQ", "AppY", LocalDateTime.now());

        when(messageRepository.save(input)).thenReturn(saved);

        MessageResponseDTO result = messageService.saveMessage(input);
        assertEquals(10L, result.getId());
    }
}