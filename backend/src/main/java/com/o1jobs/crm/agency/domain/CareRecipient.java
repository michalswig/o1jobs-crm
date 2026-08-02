package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @Column(length = 2000)
    private String diseasesNotes;
    @Column(nullable = false)
    private boolean smoker;
    @Column(nullable = false)
    private boolean hasPets;
    @Column(length = 2000)
    private String petsNotes;
    @Column(length = 2000)
    private String liftingAidsNotes;
    @Column(length = 2000)
    private String medicalNotes;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "care_recipient_required_capability",
            joinColumns = @JoinColumn(name = "care_recipient_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "capability")
    private Set<CareCapability> requiredCapabilities = new HashSet<>();

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

    public CareRecipient(String firstName, String lastName, LocalDate dateOfBirth, Integer heightCm, Integer weightKg,
                         Gender gender, MobilityLevel mobilityLevel, DementiaLevel dementiaLevel, String diseasesNotes,
                         boolean smoker, boolean hasPets, String petsNotes, String liftingAidsNotes, String medicalNotes,
                         Set<CareCapability> requiredCapabilities, Client client) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.gender = gender;
        this.mobilityLevel = mobilityLevel;
        this.dementiaLevel = dementiaLevel;
        this.diseasesNotes = diseasesNotes;
        this.smoker = smoker;
        this.hasPets = hasPets;
        this.petsNotes = petsNotes;
        this.liftingAidsNotes = liftingAidsNotes;
        this.medicalNotes = medicalNotes;
        if (requiredCapabilities != null) {
            this.requiredCapabilities.addAll(requiredCapabilities);
        }
        this.client = client;
    }

    public void updateDetails(String firstName, String lastName, LocalDate dateOfBirth, Integer heightCm, Integer weightKg,
                              Gender gender, MobilityLevel mobilityLevel, DementiaLevel dementiaLevel, String diseasesNotes,
                              boolean smoker, boolean hasPets, String petsNotes, String liftingAidsNotes, String medicalNotes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.gender = gender;
        this.mobilityLevel = mobilityLevel;
        this.dementiaLevel = dementiaLevel;
        this.diseasesNotes = diseasesNotes;
        this.smoker = smoker;
        this.hasPets = hasPets;
        this.petsNotes = petsNotes;
        this.liftingAidsNotes = liftingAidsNotes;
        this.medicalNotes = medicalNotes;
    }

    public void updateRequiredCapabilities(Set<CareCapability> newCapabilities) {
        this.requiredCapabilities.clear();
        if (newCapabilities != null) {
            this.requiredCapabilities.addAll(newCapabilities);
        }
    }

    public void assignClient(Client client) {
        this.client = client;
    }

    public void deactivate() {
        this.deletedAt = Instant.now();
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}