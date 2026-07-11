package com.o1jobs.crm.agency.dto;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String country,
        String city,
        String postalCode,
        String streetAddress,
        String notes,
        Long intermediary_id) {
}