package com.cacib.msgconsumer.mapper;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.enums.Direction;
import com.cacib.msgconsumer.enums.ProcessedFlowType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageMapperTest {

    private final MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

    @Test
    void shouldMapMessageRequestDtoToEntity() {
        MessageRequestDTO dto = new MessageRequestDTO();
        dto.setContent("Test message");
        dto.setPartnerAlias("BNP");

        Message entity = mapper.toEntity(dto);

        assertEquals("Test message", entity.getContent());
        assertNotNull(entity.getPartner());
        assertEquals("BNP", entity.getPartner().getAlias());
        assertNull(entity.getId());  // ignored
        assertNull(entity.getReceptionDate());  // ignored
    }

    @Test
    void shouldMapEntityToMessageResponseDto() {
        Partner partner = new Partner();
        partner.setId(1L);
        partner.setAlias("ALPHA");
        partner.setType("TYPE_X");
        partner.setDirection(Direction.INBOUND);
        partner.setApplication("APP");
        partner.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        partner.setDescription("Bank");

        Message message = new Message();
        message.setId(101L);
        message.setContent("Incoming");
        message.setPartner(partner);
        message.setReceptionDate(LocalDateTime.now());

        MessageResponseDTO dto = mapper.toMessageResponseDTO(message);

        assertEquals(101L, dto.getId());
        assertEquals("Incoming", dto.getContent());
        assertNotNull(dto.getReceptionDate());
        assertNotNull(dto.getPartner());
        assertEquals("ALPHA", dto.getPartner().getAlias());
    }
}
