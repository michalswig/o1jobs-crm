package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Caregiver;
import com.o1jobs.crm.exception.InvalidFileTypeException;
import com.o1jobs.crm.exception.NoSuchPhotoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CaregiverPhotoService {

    private static final String ALLOWED_CONTENT_TYPE = "image/jpeg";

    private final CaregiverService caregiverService;
    private final DocumentStorageService documentStorageService;

    public void upload(Long caregiverId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileTypeException("Die hochgeladene Datei ist leer.");
        }
        if (!ALLOWED_CONTENT_TYPE.equals(file.getContentType())) {
            throw new InvalidFileTypeException("Es sind nur JPG-Dateien zulässig.");
        }

        Caregiver caregiver = caregiverService.getEntityById(caregiverId);
        String previousStorageKey = caregiver.getPhotoPath();

        // Nowy, unikalny klucz przy każdym uploadzie - stare zdjęcie w R2 (jeśli było)
        // kasujemy dopiero PO udanym uploadzie nowego, żeby nie zostać bez żadnej wersji.
        String storageKey = "caregivers/" + caregiverId + "/" + UUID.randomUUID() + ".jpg";
        documentStorageService.upload(storageKey, readBytes(file), file.getContentType());

        caregiver.updatePhotoPath(storageKey);

        if (previousStorageKey != null) {
            documentStorageService.delete(previousStorageKey);
        }
    }

    @Transactional(readOnly = true)
    public byte[] download(Long caregiverId) {
        Caregiver caregiver = caregiverService.getEntityById(caregiverId);
        if (caregiver.getPhotoPath() == null) {
            throw new NoSuchPhotoException("No photo for caregiver with id " + caregiverId);
        }
        return documentStorageService.download(caregiver.getPhotoPath());
    }

    public void delete(Long caregiverId) {
        Caregiver caregiver = caregiverService.getEntityById(caregiverId);
        if (caregiver.getPhotoPath() == null) {
            throw new NoSuchPhotoException("No photo for caregiver with id " + caregiverId);
        }
        documentStorageService.delete(caregiver.getPhotoPath());
        caregiver.updatePhotoPath(null);
    }

    private byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Die hochgeladene Datei konnte nicht gelesen werden.", e);
        }
    }
}