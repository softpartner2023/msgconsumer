package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.mapper.MessageMapper;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllMessages() {
        Message msg1 = new Message();
        Message msg2 = new Message();
        when(messageRepository.findAll()).thenReturn(Arrays.asList(msg1, msg2));
        when(messageMapper.toMessageResponseDTO(any())).thenReturn(new MessageResponseDTO());

        var messages = messageService.getAllMessages();
        assertEquals(2, messages.size());
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnMessageById() {
        Message msg = new Message();
        msg.setId(1L);
        msg.setContent("Test");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(msg));
        when(messageMapper.toMessageResponseDTO(msg)).thenReturn(new MessageResponseDTO(1L, "Test", LocalDateTime.now(), new PartnerResponseDTO()));

        MessageResponseDTO result = messageService.getMessageById(1L);
        assertEquals("Test", result.getContent());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenMessageNotFound() {
        when(messageRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> messageService.getMessageById(99L));

        assertTrue(exception.getMessage().contains("Message not found"));
    }

    @Test
    void shouldSaveMessage() {
        MessageRequestDTO dto = new MessageRequestDTO();
        dto.setContent("Test MQ");
        dto.setPartnerAlias("CACIB");

        Partner partner = new Partner();
        partner.setId(1L);
        partner.setAlias("CACIB");

        Message message = new Message();
        message.setContent("Test MQ");
        message.setPartner(partner);

        MessageResponseDTO responseDTO = new MessageResponseDTO(10L, "Test MQ", LocalDateTime.now(), new PartnerResponseDTO());

        when(partnerRepository.findByAlias("CACIB")).thenReturn(Optional.of(partner));
        when(messageMapper.toEntity(dto)).thenReturn(message);
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toMessageResponseDTO(message)).thenReturn(responseDTO);

        MessageResponseDTO result = messageService.saveMessage(dto);

        assertEquals("Test MQ", result.getContent());
    }
}
