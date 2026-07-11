package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareRecipient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CareRecipientMapper {
    @Mapping(source = "client.id", target = "clientId")
    CareRecipientResponse toCareRecipientResponse(CareRecipient careRecipient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    void updateCareRecipient(CareRecipientRequest careRecipientRequest, @MappingTarget CareRecipient careRecipient);
}
