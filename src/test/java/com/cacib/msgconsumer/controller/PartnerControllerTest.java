package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import com.cacib.msgconsumer.repository.PartnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PartnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        partnerRepository.deleteAll();
    }

    @Test
    void shouldAddPartner() throws Exception {
        PartnerRequestDTO partner = new PartnerRequestDTO("AliasX", "TypeA", Direction.INBOUND, "AppX", ProcessedFlowType.MESSAGE, "DescX");

        mockMvc.perform(post("/api/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partner)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.alias", is("AliasX")));
    }

    @Test
    void shouldListAllPartners() throws Exception {
        partnerRepository.save(new Partner(null, "Alias1", "Type1", Direction.INBOUND, "App1", ProcessedFlowType.MESSAGE, "Desc1", new ArrayList<>()));
        partnerRepository.save(new Partner(null, "Alias2", "Type2", Direction.OUTBOUND, "App2", ProcessedFlowType.ALERTING, "Desc2", new ArrayList<>()));

        mockMvc.perform(get("/api/partners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void shouldDeletePartnerById() throws Exception {
        Partner partner = new Partner(null, "AliasToDelete", "Type", Direction.INBOUND, "App", ProcessedFlowType.MESSAGE, "to delete", new ArrayList<>());
        Partner saved = partnerRepository.save(partner);

        mockMvc.perform(delete("/api/partners/{id}", saved.getId()))
                .andExpect(status().isOk());

        assertFalse(partnerRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldReturn400WhenAliasAlreadyExists() throws Exception {
        Partner existing = new Partner(null, "DUPLICATE_ALIAS", "Type", Direction.INBOUND, "App", ProcessedFlowType.MESSAGE, "Existing", new ArrayList<>());
        partnerRepository.save(existing);

        PartnerRequestDTO duplicate = new PartnerRequestDTO("DUPLICATE_ALIAS", "Type", Direction.INBOUND, "App", ProcessedFlowType.MESSAGE, "Duplicate");

        mockMvc.perform(post("/api/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(containsString("already exist")));
    }
}