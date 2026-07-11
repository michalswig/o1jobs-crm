package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "intermediary.id", target = "intermediary_id")
    ClientResponse toClientResponse(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "intermediary", ignore = true)
    void updateClient(ClientRequest request, @MappingTarget Client client);

}
