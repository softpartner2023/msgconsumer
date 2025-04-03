package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        messageRepository.deleteAll();
    }

    @Test
    void shouldCreateAndReturnMessage() throws Exception {
        Message msg = new Message(null, "Hello", "App1", LocalDateTime.now());

        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(msg)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("Hello")))
                .andExpect(jsonPath("$.origin", is("App1")));
    }

    @Test
    void shouldReturnAllMessages() throws Exception {
        messageRepository.save(new Message(null, "Hello", "A", LocalDateTime.now()));
        messageRepository.save(new Message(null, "World", "B", LocalDateTime.now()));

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }
}