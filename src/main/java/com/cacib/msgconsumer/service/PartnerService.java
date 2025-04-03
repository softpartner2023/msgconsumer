package com.cacib.msgconsumer.service;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;

import java.util.List;

public interface PartnerService {
    List<PartnerResponseDTO> getAllPartners();
    PartnerResponseDTO getPartnerById(Long id);
    PartnerResponseDTO addPartner(PartnerRequestDTO partnerRequestDTO);
    void deletePartner(Long id);
}
