package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "care_recipient")
@Getter
public class CareRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Integer heightCm;
    @Column(nullable = false)
    private Integer weightKg;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MobilityLevel mobilityLevel;
    @Enumerated(EnumType.STRING)
    private DementiaLevel dementiaLevel;
    @Column(nullable = false)
    private boolean hasMs;
    @Column(nullable = false)
    private boolean hasAlzheimer;
    @Column(nullable = false)
    private boolean hasParkinson;
    @Column(length = 2000)
    private String diseasesNotes;
    @Column(nullable = false)
    private boolean smoker;
    @Column(nullable = false)
    private boolean hasPets;
    @Column(length = 2000)
    private String petsNotes;
    @Column(nullable = false)
    private boolean needsTransfer;
    @Enumerated(EnumType.STRING)
    private TransferType transferType;
    @Column(length = 2000)
    private String liftingAidsNotes;
    @Column(nullable = false)
    private boolean hasCatheter;
    @Column(nullable = false)
    private boolean hasStoma;
    private boolean useDiapers;
    @Column(length = 2000)
    private String medicalNotes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private Instant createdAt;
    private Long createdByUserId;
    private Instant updatedAt;
    private Long updatedByUserId;

    private Instant deletedAt;

    protected CareRecipient() {
    }

    public CareRecipient(String firstName, String lastName, LocalDate dateOfBirth, Integer heightCm, Integer weightKg, Gender gender, MobilityLevel mobilityLevel, DementiaLevel dementiaLevel, boolean hasMs, boolean hasAlzheimer, boolean hasParkinson, String diseasesNotes, boolean smoker, boolean hasPets, String petsNotes, boolean needsTransfer, TransferType transferType, String liftingAidsNotes, boolean hasCatheter, boolean hasStoma, boolean useDiapers, String medicalNotes, Client client) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.gender = gender;
        this.mobilityLevel = mobilityLevel;
        this.dementiaLevel = dementiaLevel;
        this.hasMs = hasMs;
        this.hasAlzheimer = hasAlzheimer;
        this.hasParkinson = hasParkinson;
        this.diseasesNotes = diseasesNotes;
        this.smoker = smoker;
        this.hasPets = hasPets;
        this.petsNotes = petsNotes;
        this.needsTransfer = needsTransfer;
        this.transferType = transferType;
        this.liftingAidsNotes = liftingAidsNotes;
        this.hasCatheter = hasCatheter;
        this.hasStoma = hasStoma;
        this.useDiapers = useDiapers;
        this.medicalNotes = medicalNotes;
        this.client = client;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.deletedAt = Instant.now();
    }

}
