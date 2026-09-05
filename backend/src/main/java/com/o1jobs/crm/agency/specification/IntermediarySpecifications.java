package com.o1jobs.crm.agency.specification;

import com.o1jobs.crm.agency.domain.Intermediary;
import org.springframework.data.jpa.domain.Specification;

public class IntermediarySpecifications {

    public static Specification<Intermediary> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

}