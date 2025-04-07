package com.cacib.msgconsumer.repository;

import com.cacib.msgconsumer.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    boolean existsByAlias(String alias);

    Optional<Partner> findByAlias(String alias);

}
