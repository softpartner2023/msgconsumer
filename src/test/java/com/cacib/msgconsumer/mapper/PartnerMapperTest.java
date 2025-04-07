package com.cacib.msgconsumer.mapper;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PartnerMapperTest {

    private final PartnerMapper mapper = Mappers.getMapper(PartnerMapper.class);

    @Test
    void shouldMapRequestDtoToEntity() {
        PartnerRequestDTO dto = new PartnerRequestDTO();
        dto.setAlias("PARTNER_001");
        dto.setType("TYPE_X");
        dto.setDirection(Direction.INBOUND);
        dto.setApplication("MyApp");
        dto.setProcessedFlowType(ProcessedFlowType.ALERTING);
        dto.setDescription("Test partner");

        Partner entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.getAlias(), entity.getAlias());
        assertEquals(dto.getType(), entity.getType());
        assertEquals(dto.getDirection(), entity.getDirection());
        assertEquals(dto.getApplication(), entity.getApplication());
        assertEquals(dto.getProcessedFlowType(), entity.getProcessedFlowType());
        assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    void shouldMapEntityToResponseDto() {
        Partner entity = new Partner();
        entity.setId(100L);
        entity.setAlias("ALIAS_100");
        entity.setType("TYPE_Y");
        entity.setDirection(Direction.OUTBOUND);
        entity.setApplication("MyBackend");
        entity.setProcessedFlowType(ProcessedFlowType.NOTIFICATION);
        entity.setDescription("Desc de test");

        PartnerResponseDTO dto = mapper.toPartnerResponseDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAlias(), dto.getAlias());
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getDirection(), dto.getDirection());
        assertEquals(entity.getApplication(), dto.getApplication());
        assertEquals(entity.getProcessedFlowType(), dto.getProcessedFlowType());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}
