package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long>, JpaSpecificationExecutor<Caregiver> {
}
