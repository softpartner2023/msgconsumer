package com.cacib.msgconsumer.listener;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MqMessageListener {

    private static final Logger log = LoggerFactory.getLogger(MqMessageListener.class);

    private final ConnectionFactory connectionFactory;
    private final Queue queue;
    private final MessageService messageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void startListening() {
        new Thread(() -> {
            try (Connection connection = connectionFactory.createConnection();
                 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

                connection.start();
                MessageConsumer consumer = session.createConsumer(queue);

                log.info("MQ Listener started — waiting for messages on queue: {}", queue.getQueueName());

                while (true) {
                    TextMessage textMessage = (TextMessage) consumer.receive();
                    String json = textMessage.getText();
                    log.info("Received raw JSON from MQ: {}", json);

                    MessageRequestDTO msg = objectMapper.readValue(json, MessageRequestDTO.class);

                    MessageResponseDTO msgSaved = messageService.saveMessage(msg);

                    log.info("Message saved to database [ID: {}]", msgSaved.getId());
                }

            } catch (Exception e) {
                log.error("Error in the MQ Listener : {}", e.getMessage());
            }
        }).start();
    }
}
