package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import com.cacib.msgconsumer.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerServiceImplTest {

    private PartnerRepository partnerRepository;
    private PartnerServiceImpl partnerService;

    @BeforeEach
    void setUp() {
        partnerRepository = mock(PartnerRepository.class);
        partnerService = new PartnerServiceImpl(partnerRepository);
    }

    @Test
    void shouldReturnAllPartners() {
        when(partnerRepository.findAll()).thenReturn(Arrays.asList(
                new Partner(1L, "Alias1", "Type1", Direction.INBOUND, "App1", ProcessedFlowType.MESSAGE, "Desc1"),
                new Partner(2L, "Alias2", "Type2", Direction.OUTBOUND, "App2", ProcessedFlowType.NOTIFICATION, "Desc2")
        ));

        var list = partnerService.getAllPartners();
        assertEquals(2, list.size());
    }

    @Test
    void shouldReturnPartnerById() {
        Partner partner = new Partner(1L, "Alias1", "Type1", Direction.INBOUND, "App1", ProcessedFlowType.MESSAGE, "Desc1");

        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));

        var result = partnerService.getPartnerById(1L);
        assertEquals("Alias1", result.getAlias());
    }

    @Test
    void shouldThrowIfPartnerNotFound() {
        when(partnerRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> partnerService.getPartnerById(99L));

        assertTrue(exception.getMessage().contains("Partner not found"));
    }

    @Test
    void shouldSavePartner() {
        Partner input = new Partner(null, "Alias1", "Type1", Direction.INBOUND, "App1", ProcessedFlowType.MESSAGE, "Desc1");
        Partner saved = new Partner(1L, "Alias1", "Type1", Direction.INBOUND, "App1", ProcessedFlowType.MESSAGE, "Desc1");

        when(partnerRepository.save(input)).thenReturn(saved);

        Partner result = partnerService.addPartner(input);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldDeletePartner() {
        doNothing().when(partnerRepository).deleteById(1L);
        partnerService.deletePartner(1L);
        verify(partnerRepository, times(1)).deleteById(1L);
    }
}