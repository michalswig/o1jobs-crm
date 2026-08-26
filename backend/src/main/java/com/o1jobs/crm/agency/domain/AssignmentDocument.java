package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "assignment_document")
@Getter
public class AssignmentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", unique = true)
    private Assignment assignment;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String storageKey;

    private Instant uploadedAt;
    private Long uploadedByUserId;

    protected AssignmentDocument() {
    }

    public AssignmentDocument(Assignment assignment, String fileName, String contentType,
                              long fileSize, String storageKey) {
        this.assignment = assignment;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.storageKey = storageKey;
    }

    public void replace(String fileName, String contentType, long fileSize, String storageKey) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.storageKey = storageKey;
    }

    @PrePersist
    public void onCreate() {
        this.uploadedAt = Instant.now();
    }
}