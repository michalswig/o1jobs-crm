package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
