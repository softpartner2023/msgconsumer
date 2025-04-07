package com.cacib.msgconsumer.mapper;

import com.cacib.msgconsumer.dto.MessageRequestDTO;
import com.cacib.msgconsumer.dto.MessageResponseDTO;
import com.cacib.msgconsumer.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "partner", target = "partner")
    MessageResponseDTO toMessageResponseDTO(Message message);

    @Mapping(source = "partnerAlias", target = "partner.alias")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receptionDate", ignore = true)
    Message toEntity(MessageRequestDTO dto);
}
