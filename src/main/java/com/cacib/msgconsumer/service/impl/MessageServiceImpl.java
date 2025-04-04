package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.mapper.MessageMapper;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.service.MessageService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageResponseDTO> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(MessageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id : " + id));
        return MessageMapper.toResponse(message);
    }

    @Override
    public MessageResponseDTO saveMessage(Message message) {
        Message savedMessage = messageRepository.save(message);
        return MessageMapper.toResponse(savedMessage);
    }
}
