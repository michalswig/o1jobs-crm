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
    private String requirements;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;
    @Enumerated(EnumType.STRING)
    private AssignmentCloseReason closeReason;
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

    public Assignment(Client client, CareRecipient careRecipient, LocalDate startDate, String city, String streetAddress, BigDecimal salaryMonthlyNet, LanguageLevel languageLevel, String requirements, AssignmentStatus status, AssignmentCloseReason closeReason, String closeNotes, Caregiver caregiver) {
        this.client = client;
        this.careRecipient = careRecipient;
        this.startDate = startDate;
        this.city = city;
        this.streetAddress = streetAddress;
        this.salaryMonthlyNet = salaryMonthlyNet;
        this.languageLevel = languageLevel;
        this.requirements = requirements;
        this.status = status;
        this.closeReason = closeReason;
        this.closeNotes = closeNotes;
        this.caregiver = caregiver;
    }

    public void close(AssignmentCloseReason reason, String notes) {
        this.status = AssignmentStatus.CLOSED;
        this.closeReason = reason;
        this.deletedAt = Instant.now();
        this.closeNotes = notes;
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
