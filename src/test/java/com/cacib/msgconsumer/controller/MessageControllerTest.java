package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import com.cacib.msgconsumer.repository.MessageRepository;
import com.cacib.msgconsumer.repository.PartnerRepository;
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
    private PartnerRepository partnerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        messageRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    void shouldReturnAllMessages() throws Exception {
        // Given
        Partner partner = new Partner();
        partner.setAlias("CA");
        partner.setType("INTERNE");
        partner.setApplication("Trade");
        partner.setDirection(Direction.OUTBOUND);
        partner.setProcessedFlowType(ProcessedFlowType.NOTIFICATION);
        partner.setDescription("Crédit Agricole");

        partnerRepository.save(partner);

        Message msg1 = new Message();
        msg1.setContent("msg1");
        msg1.setReceptionDate(LocalDateTime.now());
        msg1.setPartner(partner);

        Message msg2 = new Message();
        msg2.setContent("msg2");
        msg2.setReceptionDate(LocalDateTime.now());
        msg2.setPartner(partner);

        messageRepository.save(msg1);
        messageRepository.save(msg2);

        // When + Then
        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].partner.alias", is("CA")));
    }
}
