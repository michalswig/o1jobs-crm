package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "caregiver")
@Getter
public class Caregiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private Integer weightKg;
    @Column(nullable = false)
    private Integer heightCm;
    @Column(nullable = false)
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private Nationality nationality;
    @Column(nullable = false)
    private LocalDate careerStartDate;
    @Column(nullable = false)
    private boolean hasDriverLicense;
    @Column(nullable = false)
    private boolean smoker;
    @Column(length = 2000)
    private String medicalQualificationNotes;
    @Column(length = 2000)
    private String recruiterNotes;

    private Instant createdAt;
    private Long createdByUserId;
    private Instant updatedAt;
    private Long updatedByUserId;

    private Instant deletedAt;

    protected Caregiver() {
    }

    public Caregiver(String firstName, String lastName, Gender gender, LocalDate birthDate, Integer weightKg, Integer heightCm, String phone, String email, Nationality nationality, LocalDate careerStartDate, boolean hasDriverLicense, boolean smoker, String medicalQualificationNotes, String recruiterNotes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
        this.careerStartDate = careerStartDate;
        this.hasDriverLicense = hasDriverLicense;
        this.smoker = smoker;
        this.medicalQualificationNotes = medicalQualificationNotes;
        this.recruiterNotes = recruiterNotes;
    }

    public void updateDetails(String firstName, String lastName, Gender gender, LocalDate birthDate, Integer weightKg, Integer heightCm, String phone, String email, Nationality nationality, LocalDate careerStartDate, boolean hasDriverLicense, boolean smoker, String medicalQualificationNotes, String recruiterNotes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.phone = phone;
        this.email = email;
        this.nationality = nationality;
        this.careerStartDate = careerStartDate;
        this.hasDriverLicense = hasDriverLicense;
        this.smoker = smoker;
        this.medicalQualificationNotes = medicalQualificationNotes;
        this.recruiterNotes = recruiterNotes;
    }

    public Integer getExperienceYears() {
        return Period.between(this.careerStartDate, LocalDate.now()).getYears();
    }

    public void softDelete() {
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
