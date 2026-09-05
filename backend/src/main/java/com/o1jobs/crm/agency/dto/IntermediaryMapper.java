package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Intermediary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IntermediaryMapper {
    IntermediaryResponse toIntermediaryResponse(Intermediary intermediary);
}