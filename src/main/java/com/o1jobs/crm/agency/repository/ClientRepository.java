package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByIntermediary_Id(Long intermediaryId);

    List<Client> id(Long id);
}
