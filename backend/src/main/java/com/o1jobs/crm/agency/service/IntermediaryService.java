package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Intermediary;
import com.o1jobs.crm.agency.dto.IntermediaryMapper;
import com.o1jobs.crm.agency.dto.IntermediaryRequest;
import com.o1jobs.crm.agency.dto.IntermediaryResponse;
import com.o1jobs.crm.agency.repository.IntermediaryRepository;
import com.o1jobs.crm.agency.specification.IntermediarySpecifications;
import com.o1jobs.crm.exception.NoSuchIntermediaryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IntermediaryService {
    private final IntermediaryRepository intermediaryRepository;
    private final IntermediaryMapper intermediaryMapper;

    @Transactional(readOnly = true)
    public Page<IntermediaryResponse> getAll(Pageable pageable) {
        return intermediaryRepository.findAll(IntermediarySpecifications.notDeleted(), pageable)
                .map(intermediaryMapper::toIntermediaryResponse);
    }

    @Transactional(readOnly = true)
    public IntermediaryResponse getById(Long id) {
        return intermediaryMapper.toIntermediaryResponse(getEntityById(id));
    }

    public Intermediary getEntityById(Long id) {
        return intermediaryRepository.findById(id).orElseThrow(
                () -> new NoSuchIntermediaryException("Intermediary with id " + id + " does not exist")
        );
    }

    public IntermediaryResponse create(IntermediaryRequest request) {
        Intermediary intermediary = new Intermediary(
                null,
                request.notes(),
                request.phone(),
                request.email(),
                request.streetAddress(),
                request.postalCode(),
                request.city(),
                request.country(),
                request.name(),
                request.intermediaryType()
        );
        return intermediaryMapper.toIntermediaryResponse(intermediaryRepository.save(intermediary));
    }

    public IntermediaryResponse update(Long id, IntermediaryRequest request) {
        Intermediary intermediary = getEntityById(id);
        intermediary.updateDetails(
                request.name(), request.email(), request.phone(), request.streetAddress(),
                request.postalCode(), request.city(), request.country(), request.notes(),
                request.intermediaryType()
        );
        return intermediaryMapper.toIntermediaryResponse(intermediary);
    }

    public void deactivate(Long id) {
        getEntityById(id).deactivate();
    }
}