package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "intermediary")
@Getter
public class Intermediary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IntermediaryType intermediaryType;
    @Column(nullable = false)
    private String name;
    private String country;
    private String city;
    private String postalCode;
    private String streetAddress;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(length = 2000)
    private String notes;

    private Instant createdAt;
    private Long createdByUserId;
    private Instant updatedAt;
    private Long updatedByUserId;

    private Instant deletedAt;

    protected Intermediary() {
    }

    public Intermediary(Long createdByUserId, String notes, String phone, String email, String streetAddress, String postalCode, String city, String country, String name, IntermediaryType intermediaryType) {
        this.createdByUserId = createdByUserId;
        this.notes = notes;
        this.phone = phone;
        this.email = email;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.name = name;
        this.intermediaryType = intermediaryType;
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