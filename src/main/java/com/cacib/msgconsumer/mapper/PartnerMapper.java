package com.cacib.msgconsumer.mapper;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    PartnerResponseDTO toPartnerResponseDTO(Partner partner);

    Partner toEntity(PartnerRequestDTO dto);

}
