package com.cacib.msgconsumer.mapper;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;

public class PartnerMapper {
    public static Partner toEntity(PartnerRequestDTO dto) {
        return new Partner(
                null, // id is null in creation
                dto.getAlias(),
                dto.getType(),
                dto.getDirection(),
                dto.getApplication(),
                dto.getProcessedFlowType(),
                dto.getDescription()
        );
    }

    public static PartnerResponseDTO toResponse(Partner partner) {
        return new PartnerResponseDTO(
                partner.getId(),
                partner.getAlias(),
                partner.getType(),
                partner.getDirection(),
                partner.getApplication(),
                partner.getProcessedFlowType(),
                partner.getDescription()
        );
    }
}
