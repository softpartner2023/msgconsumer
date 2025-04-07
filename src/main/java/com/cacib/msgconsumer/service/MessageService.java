package com.cacib.msgconsumer.service;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import java.util.List;

public interface MessageService {
    List<MessageResponseDTO> getAllMessages();
    MessageResponseDTO getMessageById(Long id);
    MessageResponseDTO saveMessage(MessageRequestDTO messageRequestDTO);
}
