package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, CareRecipientMapper.class, CaregiverMapper.class})
public interface AssignmentMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "caregiver.id", target = "caregiverId")
    @Mapping(source = "careRecipient.id", target = "careRecipientId")
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    AssignmentDetailResponse toAssignmentDetailResponse(Assignment assignment);
}