package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.Intermediary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntermediaryRepository extends JpaRepository<Intermediary, Long>, JpaSpecificationExecutor<Intermediary> {
}