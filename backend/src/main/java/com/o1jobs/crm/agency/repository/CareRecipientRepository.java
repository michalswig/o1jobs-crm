package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.CareRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CareRecipientRepository extends JpaRepository<CareRecipient, Long>, JpaSpecificationExecutor<CareRecipient> {
}