package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareRecipient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CareRecipientMapper {
    @Mapping(source = "client.id", target = "clientId")
    CareRecipientResponse toCareRecipientResponse(CareRecipient careRecipient);
}