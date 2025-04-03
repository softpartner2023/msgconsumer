package com.cacib.msgconsumer.controller;

import com.cacib.msgconsumer.dto.PartnerRequestDTO;
import com.cacib.msgconsumer.dto.PartnerResponseDTO;
import com.cacib.msgconsumer.entity.Partner;
import com.cacib.msgconsumer.service.PartnerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@CrossOrigin(origins = "*")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public List<PartnerResponseDTO> getAllPartners() {
        return partnerService.getAllPartners();
    }

    @GetMapping("/{id}")
    public PartnerResponseDTO getPartnerById(@PathVariable Long id) {
        return partnerService.getPartnerById(id);
    }

    @PostMapping
    public PartnerResponseDTO addPartner(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {
        return partnerService.addPartner(partnerRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
    }
}
