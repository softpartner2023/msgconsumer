package com.cacib.msgconsumer.service;

import com.cacib.msgconsumer.entity.Partner;

import java.util.List;

public interface PartnerService {
    List<Partner> getAllPartners();
    Partner getPartnerById(Long id);
    Partner addPartner(Partner partner);
    void deletePartner(Long id);
}
