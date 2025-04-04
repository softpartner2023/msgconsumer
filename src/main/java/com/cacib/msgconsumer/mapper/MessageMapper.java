package com.cacib.msgconsumer.mapper;


import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;

import java.time.LocalDateTime;

public class MessageMapper {

    public static Message toEntity(MessageRequestDTO dto) {
        Message message = new Message();
        message.setContent(dto.getContent());
        message.setOrigin(dto.getOrigin());
        message.setReceptionDate(LocalDateTime.now());
        return message;
    }

    public static MessageResponseDTO toResponse(Message entity) {
        return new MessageResponseDTO(
                entity.getId(),
                entity.getContent(),
                entity.getOrigin(),
                entity.getReceptionDate()
        );
    }
}
