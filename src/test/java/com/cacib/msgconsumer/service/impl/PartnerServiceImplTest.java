package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import com.cacib.msgconsumer.exception.BadRequestException;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.mapper.PartnerMapper;
import com.cacib.msgconsumer.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartnerServiceImplTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPartners() {
        Partner p1 = new Partner();
        Partner p2 = new Partner();
        when(partnerRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        when(partnerMapper.toPartnerResponseDTO(any())).thenReturn(new PartnerResponseDTO());

        List<PartnerResponseDTO> result = partnerService.getAllPartners();

        assertEquals(2, result.size());
        verify(partnerRepository).findAll();
    }

    @Test
    void shouldReturnPartnerById() {
        Partner partner = new Partner();
        partner.setId(1L);
        partner.setAlias("CACIB");

        PartnerResponseDTO dto = new PartnerResponseDTO();
        dto.setId(1L);
        dto.setAlias("CACIB");

        when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
        when(partnerMapper.toPartnerResponseDTO(partner)).thenReturn(dto);

        PartnerResponseDTO result = partnerService.getPartnerById(1L);
        assertEquals("CACIB", result.getAlias());
    }

    @Test
    void shouldThrowWhenPartnerNotFound() {
        when(partnerRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> partnerService.getPartnerById(42L));
    }

    @Test
    void shouldAddNewPartner() {
        PartnerRequestDTO request = new PartnerRequestDTO("CACIB", "TYPE_A", Direction.INBOUND, "APP_X", ProcessedFlowType.MESSAGE, "test");
        Partner entity = new Partner();
        entity.setAlias("CACIB");

        Partner saved = new Partner();
        saved.setId(99L);
        saved.setAlias("CACIB");

        PartnerResponseDTO response = new PartnerResponseDTO();
        response.setId(99L);
        response.setAlias("CACIB");

        when(partnerRepository.existsByAlias("CACIB")).thenReturn(false);
        when(partnerMapper.toEntity(request)).thenReturn(entity);
        when(partnerRepository.save(entity)).thenReturn(saved);
        when(partnerMapper.toPartnerResponseDTO(saved)).thenReturn(response);

        PartnerResponseDTO result = partnerService.addPartner(request);

        assertEquals("CACIB", result.getAlias());
        assertEquals(99L, result.getId());
    }

    @Test
    void shouldRejectDuplicateAlias() {
        PartnerRequestDTO request = new PartnerRequestDTO("DUPLICATE", "TYPE_A", Direction.INBOUND, "APP", ProcessedFlowType.NOTIFICATION, "Dup");
        when(partnerRepository.existsByAlias("DUPLICATE")).thenReturn(true);
        Partner partner = new Partner();
        partner.setAlias("DUPLICATE");
        when(partnerMapper.toEntity(request)).thenReturn(partner);

        assertThrows(BadRequestException.class, () -> partnerService.addPartner(request));
    }

    @Test
    void shouldDeletePartner() {
        partnerService.deletePartner(1L);
        verify(partnerRepository).deleteById(1L);
    }
}