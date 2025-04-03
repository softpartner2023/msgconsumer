package com.cacib.msgconsumer.service;

import com.cacib.msgconsumer.entity.Message;
import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    Message saveMessage(Message message);
}
