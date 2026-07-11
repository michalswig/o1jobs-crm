package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Caregiver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CaregiverMapper {
    CaregiverResponse caregiverToCaregiverResponse(Caregiver caregiver);

    Caregiver caregiverRequestToCaregiver(CaregiverRequest request);

    void updateCaregiver(CaregiverRequest request, @MappingTarget Caregiver caregiver);
}