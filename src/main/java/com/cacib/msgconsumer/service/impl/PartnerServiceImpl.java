package com.cacib.msgconsumer.service.impl;

import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.exception.BadRequestException;
import com.cacib.msgconsumer.exception.ResourceNotFoundException;
import com.cacib.msgconsumer.repository.PartnerRepository;
import com.cacib.msgconsumer.service.PartnerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    @Override
    public Partner getPartnerById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found by id : " + id));
    }

    @Override
    public Partner addPartner(Partner partner) {
        if (partnerRepository.existsByAlias(partner.getAlias())) {
            throw new BadRequestException("partner already exist with alias : " + partner.getAlias());
        }
        return partnerRepository.save(partner);
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}
