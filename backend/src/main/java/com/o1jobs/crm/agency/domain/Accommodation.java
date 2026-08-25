package com.o1jobs.crm.agency.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Accommodation {

    @Enumerated(EnumType.STRING)
    @Column(name = "accommodation_type")
    private AccommodationType type;

    @Column(name = "has_own_bathroom")
    private boolean ownBathroom;

    @Column(name = "has_own_room")
    private boolean ownRoom;

    protected Accommodation() {
    }

    public Accommodation(AccommodationType type, boolean ownBathroom, boolean ownRoom) {
        this.type = type;
        this.ownBathroom = ownBathroom;
        this.ownRoom = ownRoom;
    }
}