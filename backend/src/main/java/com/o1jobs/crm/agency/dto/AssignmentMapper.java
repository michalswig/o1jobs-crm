package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "caregiver.id", target = "caregiverId")
    @Mapping(source = "careRecipient.id", target = "careRecipientId")
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "careRecipient", ignore = true)
    @Mapping(target = "caregiver", ignore = true)
    void updateAssignmentResponse(AssignmentRequest assignmentRequest, @MappingTarget Assignment assignment);
}