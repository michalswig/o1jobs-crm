package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Caregiver;
import com.o1jobs.crm.agency.dto.CaregiverMapper;
import com.o1jobs.crm.agency.dto.CaregiverRequest;
import com.o1jobs.crm.agency.dto.CaregiverResponse;
import com.o1jobs.crm.agency.repository.CaregiverRepository;
import com.o1jobs.crm.exception.NoSuchCaregiverException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CaregiverService {
    private final CaregiverRepository caregiverRepository;
    private final CaregiverMapper caregiverMapper;

    @Transactional(readOnly = true)
    public CaregiverResponse getById(Long id) {
        Caregiver caregiver = caregiverRepository.findById(id).orElseThrow(
                () -> new NoSuchCaregiverException("No caregiver with id " + id + " was found")
        );
        return caregiverMapper.caregiverToCaregiverResponse(caregiver);
    }

    public CaregiverResponse create(CaregiverRequest request) {
        Caregiver savedCaregiver = caregiverRepository.save(caregiverMapper.caregiverRequestToCaregiver(request));
        return caregiverMapper.caregiverToCaregiverResponse(savedCaregiver);
    }

    public CaregiverResponse update(CaregiverRequest request, Long caregiverId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId).orElseThrow(
                () -> new NoSuchCaregiverException("No caregiver with id " + caregiverId + " was found")
        );
        caregiverMapper.updateCaregiver(request, caregiver);
        return caregiverMapper.caregiverToCaregiverResponse(caregiver);
    }

    public void deactivate(Long id) {
        Caregiver caregiver = caregiverRepository.findById(id).orElseThrow(
                () -> new NoSuchCaregiverException("No caregiver with id " + id + " was found")
        );
        caregiver.softDelete();
    }

}
