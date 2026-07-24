package com.o1jobs.crm.agency.specification;

import com.o1jobs.crm.agency.domain.Caregiver;
import org.springframework.data.jpa.domain.Specification;

public class CaregiverSpecifications {

    public static Specification<Caregiver> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }
}