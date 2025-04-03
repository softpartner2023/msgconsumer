package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.repository.PartnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        Partner partner = new Partner(null, "AliasX", "TypeA", "INBOUND", "AppX", "MESSAGE", "DescX");

        mockMvc.perform(post("/api/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias", is("AliasX")));
    }

    @Test
    void shouldListAllPartners() throws Exception {
        partnerRepository.save(new Partner(null, "Alias1", "Type1", "INBOUND", "App1", "MESSAGE", "Desc1"));
        partnerRepository.save(new Partner(null, "Alias2", "Type2", "OUTBOUND", "App2", "ALERTING", "Desc2"));

        mockMvc.perform(get("/api/partners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void shouldDeletePartenaireById() throws Exception {
        // On insère un partenaire dans la BDD
        Partner partner = new Partner(null, "AliasToDelete", "Type", "INBOUND", "App", "MESSAGE", "to delete");
        Partner saved = partnerRepository.save(partner);

        // On appelle l'API DELETE
        mockMvc.perform(delete("/api/partners/{id}", saved.getId()))
                .andExpect(status().isOk());

        // Vérifie qu'il n'est plus en base
        boolean stillExists = partnerRepository.findById(saved.getId()).isPresent();
        assertFalse(stillExists);
    }
}