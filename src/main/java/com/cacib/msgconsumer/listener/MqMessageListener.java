package com.cacib.msgconsumer.listener;

import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.repository.MessageRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.time.LocalDateTime;

@Component
public class MqMessageListener {

    private static final Logger log = LoggerFactory.getLogger(MqMessageListener.class);

    private final ConnectionFactory connectionFactory;
    private final Queue queue;
    private final MessageRepository messageRepository;

    public MqMessageListener(ConnectionFactory connectionFactory, Queue queue, MessageRepository messageRepository) {
        this.connectionFactory = connectionFactory;
        this.queue = queue;
        this.messageRepository = messageRepository;
    }

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
                    String content = textMessage.getText();

                    log.info("Received message from MQ: {}", content);

                    Message msg = new Message();
                    msg.setContent(content);
                    msg.setOrigin("IBM_MQ");
                    msg.setReceptionDate(LocalDateTime.now());

                    messageRepository.save(msg);

                    log.info("Message saved to database [ID: {}]", msg.getId());
                }

            } catch (Exception e) {
                log.error("Error in the MQ Listener : {}", e.getMessage());
            }
        }).start();
    }
}
