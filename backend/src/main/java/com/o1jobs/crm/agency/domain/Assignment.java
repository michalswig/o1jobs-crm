package com.o1jobs.crm.agency.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
@Getter
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "care_recipient_id")
    private CareRecipient careRecipient;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private String city;
    private String streetAddress;
    @Column(nullable = false)
    private BigDecimal salaryMonthlyNet;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguageLevel languageLevel;
    @Column(length = 2000)
    private String requirements;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;
    @Enumerated(EnumType.STRING)
    private AssignmentCloseReason closeReason;
    @Column(length = 2000)
    private String closeNotes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    private Instant createdAt;
    private Long createdByUserId;
    private Instant updatedAt;
    private Long updatedByUserId;
    private Instant deletedAt;

    protected Assignment() {
    }

    public Assignment(Client client, CareRecipient careRecipient, LocalDate startDate, String city,
                      String streetAddress, BigDecimal salaryMonthlyNet, LanguageLevel languageLevel,
                      String requirements, Caregiver caregiver) {
        this.client = client;
        this.careRecipient = careRecipient;
        this.startDate = startDate;
        this.city = city;
        this.streetAddress = streetAddress;
        this.salaryMonthlyNet = salaryMonthlyNet;
        this.languageLevel = languageLevel;
        this.requirements = requirements;
        this.status = AssignmentStatus.OPEN;
        this.caregiver = caregiver;
    }

    public void updateDetails(LocalDate startDate, String city, String streetAddress, BigDecimal salaryMonthlyNet,
                              LanguageLevel languageLevel, String requirements) {
        this.startDate = startDate;
        this.city = city;
        this.streetAddress = streetAddress;
        this.salaryMonthlyNet = salaryMonthlyNet;
        this.languageLevel = languageLevel;
        this.requirements = requirements;
    }

    public void assignCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public void close(AssignmentCloseReason reason, String notes) {
        if (this.status == AssignmentStatus.CLOSED) {
            throw new IllegalStateException("Assignment with id " + this.id + " is already closed");
        }
        this.status = AssignmentStatus.CLOSED;
        this.closeReason = reason;
        this.closeNotes = notes;
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