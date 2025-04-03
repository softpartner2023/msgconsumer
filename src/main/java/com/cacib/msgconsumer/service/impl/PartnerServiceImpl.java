package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.exception.BadRequestException;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.mapper.PartnerMapper;
import com.cacib.msgconsumer.repository.PartnerRepository;
import com.cacib.msgconsumer.service.PartnerService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public List<PartnerResponseDTO> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(PartnerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PartnerResponseDTO getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found by id : " + id));
        return PartnerMapper.toResponse(partner);
    }

    @Override
    public PartnerResponseDTO addPartner(PartnerRequestDTO partnerRequestDTO) {
        Partner partner = PartnerMapper.toEntity(partnerRequestDTO);
        if (partnerRepository.existsByAlias(partner.getAlias())) {
            throw new BadRequestException("partner already exist with alias : " + partner.getAlias());
        }
        Partner partnerSaved = partnerRepository.save(partner);
        return PartnerMapper.toResponse(partnerSaved);
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}
