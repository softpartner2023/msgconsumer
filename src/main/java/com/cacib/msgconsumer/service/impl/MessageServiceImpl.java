package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.service.MessageService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id : " + id));
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
