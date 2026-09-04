package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Caregiver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CaregiverMapper {
    @Mapping(target = "hasPhoto", expression = "java(caregiver.getPhotoPath() != null)")
    CaregiverResponse caregiverToCaregiverResponse(Caregiver caregiver);

    Caregiver caregiverRequestToCaregiver(CaregiverRequest request);
}